plugins {
    id("java")
    id("application")
}

application {
    mainClass.set("org.pustoslov.TextCaseConverter")}

group = "org.pustoslov"
version = "1.1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "org.pustoslov.TextCaseConverter" // Specify the Main-Class attribute
    }
}