plugins {
  `java-library`
  id("io.papermc.paperweight.userdev") version "1.5.5"
  id("xyz.jpenilla.run-paper") version "2.1.0" // Adds runServer and runMojangMappedServer tasks for testing
  id("com.github.johnrengelman.shadow") version "7.1.2"
  `maven-publish`
}

group = "lee.code.playerdata"
version = "1.0.0"
description = "Player data plugin."

java {
  // Configure the java toolchain. This allows gradle to auto-provision JDK 17 on systems that only have JDK 8 installed for example.
  toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

publishing {
  publications {
    create<MavenPublication>("maven") {
      groupId = "lee.code.playerdata"
      artifactId = "playerdata"
      version = "1.0.0"

      from(components["java"])
    }
  }
}

dependencies {
  paperweight.paperDevBundle("1.20.1-R0.1-SNAPSHOT")
  // paperweight.foliaDevBundle("1.20.1-R0.1-SNAPSHOT")
  // paperweight.devBundle("com.example.paperfork", "1.20.1-R0.1-SNAPSHOT")
  //lombok
  compileOnly("org.projectlombok:lombok:1.18.26")
  annotationProcessor("org.projectlombok:lombok:1.18.26")

  //ormlite
  implementation ("com.j256.ormlite:ormlite-core:6.1")
  implementation ("com.j256.ormlite:ormlite-jdbc:6.1")
}

tasks {
  // Configure reobfJar to run when invoking the build task
  assemble {
    dependsOn(reobfJar)
  }

  compileJava {
    options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything

    // Set the release flag. This configures what version bytecode the compiler will emit, as well as what JDK APIs are usable.
    // See https://openjdk.java.net/jeps/247 for more information.
    options.release.set(17)
  }
  javadoc {
    options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
  }
  processResources {
    filteringCharset = Charsets.UTF_8.name() // We want UTF-8 for everything
    val props = mapOf(
      "name" to project.name,
      "version" to project.version,
      "description" to project.description,
      "apiVersion" to "1.20"
    )
    inputs.properties(props)
    filesMatching("plugin.yml") {
      expand(props)
    }
  }

  /*
  reobfJar {
    // This is an example of how you might change the output location for reobfJar. It's recommended not to do this
    // for a variety of reasons, however it's asked frequently enough that an example of how to do it is included here.
    outputJar.set(layout.buildDirectory.file("libs/PaperweightTestPlugin-${project.version}.jar"))
  }
   */
  shadowJar {
    // Configure the dependencies to be included in the main JAR
    dependencies {
      include(dependency("com.j256.ormlite:ormlite-core:6.1"))
      include(dependency("com.j256.ormlite:ormlite-jdbc:6.1"))
    }

    // Relocate packages
    relocate("com.j256.ormlite", "lee.code.playerdata.ormlite")
  }
}
