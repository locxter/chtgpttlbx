plugins {
    kotlin("jvm") version "1.8.21"
    application
}

group = "com.github.locxter"
version = "1.0"
description = "This is a GUI ChatGPT toolbox, which is meant to make performing the same operations over and over as simple as possible."

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.formdev:flatlaf:3.1.1")
    implementation("com.aallam.openai:openai-client:3.2.4")
    implementation("io.ktor:ktor-client-okhttp:2.3.1")
    implementation("org.slf4j:slf4j-nop:2.0.7")
    implementation("com.github.kittinunf.fuel:fuel:2.3.1")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("com.github.locxter.chtgpttlbx.MainKt")
}

tasks {
    val standalone = register<Jar>("standalone") {
        dependsOn.addAll(listOf("compileJava", "compileKotlin", "processResources"))
        archiveClassifier.set("standalone")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        manifest { attributes(mapOf("Main-Class" to application.mainClass)) }
        val sourcesMain = sourceSets.main.get()
        val contents = configurations.runtimeClasspath.get()
            .map { if (it.isDirectory) it else zipTree(it) } +
                sourcesMain.output
        from(contents)
    }
    build {
        dependsOn(standalone)
    }
}
