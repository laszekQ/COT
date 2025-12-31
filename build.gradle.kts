plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation("com.github.kwhat:jnativehook:2.2.2")
    implementation("net.sourceforge.tess4j:tess4j:5.17.0")
    implementation("com.deepl.api:deepl-java:1.14.0")
}

tasks.test {
    useJUnitPlatform()
}
