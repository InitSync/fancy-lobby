plugins {
	id("com.github.johnrengelman.shadow") version("7.1.2")
	id("net.minecrell.plugin-yml.bukkit") version("0.5.2")
	`java-library`
}

val directory = property("group") as String
val release = property("version") as String
val libs = "$directory.plugin.libs"

repositories {
	mavenLocal()
	maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
	maven("https://oss.sonatype.org/content/repositories/snapshots/")
	maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
	maven("https://jitpack.io/")
	mavenCentral()
}

dependencies {
	compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
	
	compileOnly("me.clip:placeholderapi:2.11.2")
	compileOnly("net.luckperms:api:5.4")
	compileOnly("com.github.cryptomorin:XSeries:9.2.0")
	
	implementation(project(":api"))
	implementProjectAdapts(
		 "1_8_R3", "1_9_R2", "1_10_R1",
		 "1_11_R1", "1_12_R1", "1_13_R2",
		 "1_14_R1", "1_15_R1", "1_16_R3"
	)
	
	implementation("com.github.InitSync.XConfig:bukkit:1.1.4")
}

fun implementProjectAdapts(vararg adapts: String) {
	dependencies {
		for (adapt in adapts) {
			implementation(project(":adapt:v$adapt"))
		}
	}
}

bukkit {
	name = "FancyLobby"
	main = "$directory.plugin.FancyLobby"
	authors = listOf("InitSync")
	
	apiVersion = "1.13"
	version = release
	
	depend = listOf("LuckPerms")
	softDepend = listOf("PlaceholderAPI")
	
	commands {
		register("fancylobby") {
			description = "-> Command to handle the plugin."
			aliases = listOf("fl", "flobby")
		}
		
		register("scoreboard") {
			description = "-> Command to manage the plugin scoreboard."
			aliases = listOf("sb")
		}
		
		register("server") {
			description = "-> Command to use the multiple plugin utils for the server."
			aliases = listOf("sv")
		}
		
		register("language") {
			description = "-> Command to modify your current selected language."
			aliases = listOf("lang")
		}
	}
}

tasks {
	shadowJar {
		archiveFileName.set("FancyLobby-$release.jar")
		destinationDirectory.set(file("$rootDir/bin/"))
		
		relocate("net.xconfig.bukkit", "$libs.xconfig")
	}
	
	withType<JavaCompile> {
		options.encoding = "UTF-8"
	}
	
	clean {
		delete("$rootDir/bin/")
	}
}