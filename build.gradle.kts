plugins {
    id("java")
    id("org.springframework.boot") version "2.0.3.RELEASE"
    id("io.spring.dependency-management") version "1.0.5.RELEASE"
}

version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_1_9
    targetCompatibility = JavaVersion.VERSION_1_9
}

repositories {
    mavenCentral()
}

dependencies {
    compile("org.springframework.boot:spring-boot-starter-web")
    compile("org.springframework.boot:spring-boot-starter-actuator")
    compile("org.springframework.boot:spring-boot-starter-data-jpa")

    compile("com.h2database:h2")

    compile("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    compile("javax.xml.bind:jaxb-api")
    compile("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    compile("org.springframework.cloud:spring-cloud-starter-netflix-hystrix")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    testCompile("org.springframework.boot:spring-boot-starter-test")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:Finchley.RELEASE")
    }
}
