package me.initsync.fancylobby.plugin.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Objects;

public interface ItemFactory {
	static Builder of(Material material) {
		return new Builder(material);
	}
	
	class Builder {
		private final Material material;
		
		private int amount;
		private String displayName;
		private String lore;
		
		public Builder(Material material) {
			this.material = Objects.requireNonNull(material, "The material type is null.");
		}
		
		public Builder amount(int amount) {
			this.amount = (amount < 1) ? 1 : amount;
			return this;
		}
		
		public Builder displayName(String displayName) {
			this.displayName = Objects.requireNonNull(displayName, "The display name is null.");
			return this;
		}
		
		public Builder lore(String lore) {
			this.lore = Objects.requireNonNull(lore, "The lore is null.");
			return this;
		}
		
		public ItemStack build() {
			if (amount == 0) amount = 1;
			
			if (displayName == null) displayName = "";
			
			if (lore == null) lore = "";
			
			final ItemStack item = new ItemStack(material, amount);
			final ItemMeta meta = item.getItemMeta();
			
			meta.setDisplayName(displayName);
			meta.setLore(Arrays.asList(lore.split("\n")));
			meta.addItemFlags(ItemFlag.values());
			
			item.setItemMeta(meta);
			return item;
		}
	}
}
