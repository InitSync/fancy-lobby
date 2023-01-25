plugins {
	id("com.github.johnrengelman.shadow") version("7.1.2")
	`java-library`
	`maven-publish`
}

val release = property("version") as String

repositories {
	mavenLocal()
	maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
	maven("https://oss.sonatype.org/content/repositories/snapshots/")
	maven("https://jitpack.io/")
	mavenCentral()
}

dependencies {
	compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
	compileOnly("fr.mrmicky:fastboard:1.2.1")
	
	implementation("com.github.InitSync.XConfig:bukkit:1.1.4")
}

tasks {
	shadowJar {
		archiveFileName.set("FancyLobby-API-$release.jar")
		destinationDirectory.set(file("$rootDir/bin/"))
	}
	
	clean {
		delete("$rootDir/bin/")
	}
}

publishing {
	publications {
		create<MavenPublication>("maven") {
			groupId = "me.initsync.fancylobby.api"
			artifactId = "api"
			version = release
			
			from(components["java"])
		}
	}
}