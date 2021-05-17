plugins {
    id("java")
    id("org.springframework.boot") version "2.4.5"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("com.google.cloud.tools.jib") version "3.0.0"
}

version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(15))
    }
}

tasks.compileJava {
    options.compilerArgs.add("--enable-preview")
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://repo.spring.io/milestone")
    }
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2020.0.2")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j")

    implementation("com.h2database:h2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("javax.xml.bind:jaxb-api")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

jib {
    from {
        image = "adoptopenjdk/openjdk15:jre-15.0.1_9-debianslim"
    }
    to {
        image = "spring-boot-sample"
    }
    container {
        jvmFlags = listOf("--enable-preview")
        ports = listOf("8080")
    }
}
