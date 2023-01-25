plugins {
	`java-library`
}

repositories {
	mavenLocal()
	maven("https://repo.codemc.org/repository/nms/")
	mavenCentral()
}

dependencies {
	compileOnly("org.spigotmc:spigot:1.11.2-R0.1-SNAPSHOT")
	
	implementation(project(":api"))
}