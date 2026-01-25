plugins {
    id("java")
}

group = "com.github.laszekq"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    //implementation("com.github.kwhat:jnativehook:2.2.2")
    implementation(files("libs/jnativehook-2.2.2.jar"))
    implementation("net.sourceforge.tess4j:tess4j:5.17.0")
    implementation("com.deepl.api:deepl-java:1.14.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    from(configurations.runtimeClasspath.get().map {
        if (it.isDirectory) it else zipTree(it)
    })

    manifest {
        attributes["Main-Class"] = "Main"
    }

    exclude("META-INF/*.RSA", "META-INF/*.SF", "META-INF/*.DSA")

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

