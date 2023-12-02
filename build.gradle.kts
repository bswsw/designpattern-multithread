plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply {
        plugin("java")
    }

    dependencies {
        val lombokVersion = "1.18.30"
        compileOnly("org.projectlombok:lombok:$lombokVersion")
        annotationProcessor("org.projectlombok:lombok:$lombokVersion")

        testCompileOnly("org.projectlombok:lombok:$lombokVersion")
        testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")

        testImplementation(platform("org.junit:junit-bom:5.9.1"))
        testImplementation("org.junit.jupiter:junit-jupiter")
    }

    tasks {
        test {
            useJUnitPlatform()
        }
    }
}