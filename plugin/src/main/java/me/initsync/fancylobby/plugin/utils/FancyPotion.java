package me.initsync.fancylobby.plugin.utils;

import com.google.common.base.Strings;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public enum FancyPotion {
	ABSORPTION("ABSORB"),
	BAD_OMEN("OMEN_BAD", "PILLAGER"),
	BLINDNESS("BLIND"),
	CONDUIT_POWER("CONDUIT", "POWER_CONDUIT"),
	CONFUSION("NAUSEA", "SICKNESS", "SICK"),
	DAMAGE_RESISTANCE("RESISTANCE", "ARMOR", "DMG_RESIST", "DMG_RESISTANCE"),
	DARKNESS,
	DOLPHINS_GRACE("DOLPHIN", "GRACE"),
	FAST_DIGGING("HASTE", "SUPER_PICK", "DIGFAST", "DIG_SPEED", "QUICK_MINE", "SHARP"),
	FIRE_RESISTANCE("FIRE_RESIST", "RESIST_FIRE", "FIRE_RESISTANCE"),
	GLOWING("GLOW", "SHINE", "SHINY"),
	HARM("INJURE", "DAMAGE", "HARMING", "INFLICT", "INSTANT_DAMAGE"),
	HEAL("HEALTH", "INSTA_HEAL", "INSTANT_HEAL", "INSTA_HEALTH", "INSTANT_HEALTH"),
	HEALTH_BOOST("BOOST_HEALTH", "BOOST", "HP"),
	HERO_OF_THE_VILLAGE("HERO", "VILLAGE_HERO"),
	HUNGER("STARVE", "HUNGRY"),
	INCREASE_DAMAGE("STRENGTH", "BULL", "STRONG", "ATTACK"),
	INVISIBILITY("INVISIBLE", "VANISH", "INVIS", "DISAPPEAR", "HIDE"),
	JUMP("LEAP", "JUMP_BOOST"),
	LEVITATION("LEVITATE"),
	LUCK("LUCKY"),
	NIGHT_VISION("VISION", "VISION_NIGHT"),
	POISON("VENOM"),
	REGENERATION("REGEN"),
	SATURATION("FOOD"),
	SLOW("SLOWNESS", "SLUGGISH"),
	SLOW_DIGGING("FATIGUE", "DULL", "DIGGING", "SLOW_DIG", "DIG_SLOW"),
	SLOW_FALLING("SLOW_FALL", "FALL_SLOW"),
	SPEED("SPRINT", "RUNFAST", "SWIFT", "FAST"),
	UNLUCK("UNLUCKY"),
	WATER_BREATHING("WATER_BREATH", "UNDERWATER_BREATHING", "UNDERWATER_BREATH", "AIR"),
	WEAKNESS("WEAK"),
	WITHER("DECAY");
	
	/**
	 * Cached list of {@link FancyPotion#values()} to avoid allocating memory for
	 * calling the method every time.
	 *
	 * @since 1.0.0
	 */
	public static final FancyPotion[] VALUES = values();
	
	/**
	 * An unmodifiable set of "bad" potion effects.
	 *
	 * @since 1.1.0
	 */
	public static final Set<FancyPotion> DEBUFFS = Collections.unmodifiableSet(EnumSet.of(
		 BAD_OMEN, BLINDNESS, CONFUSION, HARM, HUNGER, LEVITATION, POISON,
		 SLOW, SLOW_DIGGING, UNLUCK, WEAKNESS, WITHER)
	);
	
	/**
	 * Efficient mapping to get {@link FancyPotion} from a {@link PotionEffectType}
	 * Note that <code>values.length + 1</code> is intentional as it allocates one useless space since IDs start from 1
	 */
	private static final FancyPotion[] POTIONEFFECTTYPE_MAPPING = new FancyPotion[VALUES.length + 1];
	
	static {
		for (FancyPotion pot : VALUES)
			if (pot.type != null) //noinspection deprecation
				POTIONEFFECTTYPE_MAPPING[pot.type.getId()] = pot;
	}
	
	private final PotionEffectType type;
	
	FancyPotion(String... aliases) {
		this.type = PotionEffectType.getByName(this.name());
		FancyPotion.Data.NAMES.put(this.name(), this);
		for (String legacy : aliases) FancyPotion.Data.NAMES.put(legacy, this);
	}
	
	/**
	 * Attempts to build the string like an enum name.<br>
	 * Removes all the spaces, numbers and extra non-English characters. Also removes some config/in-game based strings.
	 * While this method is hard to maintain, it's extremely efficient. It's approximately more than x5 times faster than
	 * the normal RegEx + String Methods approach for both formatted and unformatted material names.
	 *
	 * @param name the potion effect type name to format.
	 *
	 * @return an enum name.
	 * @since 1.0.0
	 */
	private static String format(String name) {
		int len = name.length();
		char[] chs = new char[len];
		int count = 0;
		boolean appendUnderline = false;
		
		for (int i = 0; i < len; i++) {
			char ch = name.charAt(i);
			
			if (!appendUnderline && count != 0 && (ch == '-' || ch == ' ' || ch == '_') && chs[count] != '_') appendUnderline = true;
			else {
				if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')) {
					if (appendUnderline) {
						chs[count++] = '_';
						appendUnderline = false;
					}
					chs[count++] = (char) (ch & 0x5f);
				}
			}
		}
		
		return new String(chs, 0, count);
	}
	
	/**
	 * Parses a potion effect type from the given string.
	 * Supports type IDs.
	 *
	 * @param potion the type of the type's ID of the potion effect type.
	 *
	 * @return a potion effect type.
	 * @since 1.0.0
	 */
	public static Optional<FancyPotion> matchXPotion(String potion) {
		if (potion == null || potion.isEmpty()) throw new IllegalArgumentException("Cannot match FancyPotion of a null or empty potion effect type");
		PotionEffectType idType = fromId(potion);
		if (idType != null) {
			FancyPotion type = FancyPotion.Data.NAMES.get(idType.getName());
			if (type == null) throw new NullPointerException("Unsupported potion effect type ID: " + idType);
			return Optional.of(type);
		}
		return Optional.ofNullable(FancyPotion.Data.NAMES.get(format(potion)));
	}
	
	/**
	 * Parses the FancyPotion for this potion effect.
	 *
	 * @param type the potion effect type.
	 *
	 * @return the FancyPotion of this potion effect.
	 * @throws IllegalArgumentException may be thrown as an unexpected exception.
	 * @since 1.0.0
	 */
	@SuppressWarnings("deprecation")
	public static FancyPotion matchXPotion(PotionEffectType type) {
		Objects.requireNonNull(type, "Cannot match FancyPotion of a null potion effect type");
		return POTIONEFFECTTYPE_MAPPING[type.getId()];
	}
	
	/**
	 * Parses the type ID if available.
	 *
	 * @param type the ID of the potion effect type.
	 *
	 * @return a potion effect type from the ID, or null if it's not an ID or the effect is not found.
	 * @since 1.0.0
	 */
	@SuppressWarnings("deprecation")
	private static PotionEffectType fromId(String type) {
		try { return PotionEffectType.getById(Integer.parseInt(type)); }
		catch (NumberFormatException ex) { return null; }
	}
	
	private static List<String> split(String str, @SuppressWarnings("SameParameterValue") char separatorChar) {
		List<String> list = new ArrayList<>(5);
		boolean match = false, lastMatch = false;
		int len = str.length();
		int start = 0;
		
		for (int i = 0; i < len; i++) {
			if (str.charAt(i) == separatorChar) {
				if (match) {
					list.add(str.substring(start, i));
					match = false;
					lastMatch = true;
				}
				
				// This is important, it should not be i++
				start = i + 1;
				continue;
			}
			
			lastMatch = false;
			match = true;
		}
		
		if (match || lastMatch) {
			list.add(str.substring(start, len));
		}
		return list;
	}
	
	/**
	 * Parse a {@link PotionEffect} from a string, usually from config.
	 * Supports potion type IDs.
	 * <br>
	 * Format: <b>Potion, Duration (in seconds), Amplifier (level) [%chance]</b>
	 * <pre>
	 *     WEAKNESS, 30, 1
	 *     SLOWNESS 200 10
	 *     1, 10000, 100 %50
	 * </pre>
	 * The last argument (the amplifier can also have a chance which if not met, returns null.
	 *
	 * @param potion the potion string to parse.
	 *
	 * @return a potion effect, or null if the potion type is wrong.
	 * @see #buildPotionEffect(int, int)
	 * @since 1.0.0
	 */
	public static FancyPotion.Effect parseEffect(String potion) {
		if (Strings.isNullOrEmpty(potion) || potion.equalsIgnoreCase("none")) return null;
		List<String> split = split(potion.replace(" ", ""), ',');
		if (split.isEmpty()) split = split(potion, ' ');
		
		double chance = 100;
		int chanceIndex = 0;
		if (split.size() > 2) {
			chanceIndex = split.get(2).indexOf('%');
			if (chanceIndex != -1) {
				try {
					chance = Double.parseDouble(split.get(2).substring(chanceIndex + 1));
				} catch (NumberFormatException ex) {
					chance = 100;
				}
			}
		}
		
		Optional<FancyPotion> typeOpt = matchXPotion(split.get(0));
		if (!typeOpt.isPresent()) return null;
		PotionEffectType type = typeOpt.get().type;
		if (type == null) return null;
		
		int duration = 2400; // 20 ticks * 60 seconds * 2 minutes
		int amplifier = 0;
		if (split.size() > 1) {
			duration = toInt(split.get(1), 1) * 20;
			if (split.size() > 2) amplifier = toInt(chanceIndex <= 0 ? split.get(2) : split.get(2).substring(0, chanceIndex), 1) - 1;
		}
		
		return new FancyPotion.Effect(new PotionEffect(type, duration, amplifier), chance);
	}
	
	private static int toInt(String str, @SuppressWarnings("SameParameterValue") int defaultValue) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return defaultValue;
		}
	}
	
	/**
	 * Add a list of potion effects to an entity from a string list, usually from config.
	 *
	 * @param entity  the entity to add potion effects to.
	 * @param effects the list of potion effects to parse and add to the entity.
	 *
	 * @see #parseEffect(String)
	 * @since 1.0.0
	 */
	public static void addEffects(LivingEntity entity, List<String> effects) {
		Objects.requireNonNull(entity, "Cannot add potion effects to null entity");
		for (FancyPotion.Effect effect : parseEffects(effects)) effect.apply(entity);
	}
	
	/**
	 * @param effectsString a list of effects with a format following {@link #parseEffect(String)}
	 *
	 * @return a list of parsed effets.
	 * @since 3.0.0
	 */
	public static List<FancyPotion.Effect> parseEffects(List<String> effectsString) {
		if (effectsString == null || effectsString.isEmpty()) return new ArrayList<>();
		List<FancyPotion.Effect> effects = new ArrayList<>(effectsString.size());
		
		for (String effectStr : effectsString) {
			FancyPotion.Effect effect = parseEffect(effectStr);
			if (effect != null) effects.add(effect);
		}
		
		return effects;
	}
	
	/**
	 * Checks if a material can have potion effects.
	 * This method does not check for {@code LEGACY} materials.
	 * You should avoid using them or use XMaterial instead.
	 *
	 * @param material the material to check.
	 *
	 * @return true if the material is a potion, otherwise false.
	 * @since 1.0.0
	 */
	public static boolean canHaveEffects(Material material) {
		return material != null && (material.name().endsWith("POTION") || material.name().startsWith("TIPPED_ARROW"));
	}
	
	/**
	 * Parses the potion effect type.
	 *
	 * @return the parsed potion effect type.
	 * @see #getPotionType()
	 * @since 1.0.0
	 */
	public PotionEffectType getPotionEffectType() {
		return this.type;
	}
	
	/**
	 * Checks if this potion is supported in the current Minecraft version.
	 * <p>
	 * An invocation of this method yields exactly the same result as the expression:
	 * <p>
	 * <blockquote>
	 * {@link #getPotionEffectType()} != null
	 * </blockquote>
	 *
	 * @return true if the current version has this potion effect type, otherwise false.
	 * @since 1.0.0
	 */
	public boolean isSupported() {
		return this.type != null;
	}
	
	/**
	 * Gets the PotionType from this PotionEffectType.
	 * Usually for potion items.
	 *
	 * @return a potion type for potions.
	 * @see #getPotionEffectType()
	 * @since 1.0.0
	 * @deprecated not for removal, but use {@link PotionEffectType} instead.
	 */
	@Deprecated
	public PotionType getPotionType() {
		return type == null ? null : PotionType.getByEffect(type);
	}
	
	/**
	 * Builds a potion effect with the given duration and amplifier.
	 *
	 * @param duration  the duration of the potion effect in ticks.
	 * @param amplifier the amplifier of the potion effect (starting from 1).
	 *
	 * @return a potion effect.
	 * @see #parseEffect(String)
	 * @since 1.0.0
	 */
	public PotionEffect buildPotionEffect(int duration, int amplifier) {
		return type == null ? null : new PotionEffect(type, duration, amplifier - 1);
	}
	
	/**
	 * In most cases you should be using {@link #name()} instead.
	 *
	 * @return a friendly readable string name.
	 */
	@Override
	public String toString() {
		return Arrays.stream(name().split("_"))
			 .map(t -> t.charAt(0) + t.substring(1).toLowerCase())
			 .collect(Collectors.joining(" "));
	}
	
	/**
	 * Used for data that need to be accessed during enum initialization.
	 *
	 * @since 2.0.0
	 */
	private static final class Data {
		private static final Map<String, FancyPotion> NAMES = new HashMap<>();
	}
	
	/**
	 * For now, this merely acts as a chance wrapper for potion effects.
	 *
	 * @since 3.0.0
	 */
	public static class Effect {
		private PotionEffect effect;
		private double chance;
		
		public Effect(PotionEffect effect, double chance) {
			this.effect = effect;
			this.chance = chance;
		}
		
		public FancyPotion getXPotion() {
			return FancyPotion.matchXPotion(effect.getType());
		}
		
		public double getChance() {
			return chance;
		}
		
		public boolean hasChance() {
			return chance >= 100 || ThreadLocalRandom.current().nextDouble(0, 100) <= chance;
		}
		
		public void setChance(double chance) {
			this.chance = chance;
		}
		
		public void apply(LivingEntity entity) {
			if (hasChance()) entity.addPotionEffect(effect);
		}
		
		public PotionEffect getEffect() {
			return effect;
		}
		
		public void setEffect(PotionEffect effect) {
			this.effect = effect;
		}
	}
}
