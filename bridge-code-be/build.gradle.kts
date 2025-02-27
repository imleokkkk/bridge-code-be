plugins {
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.4"
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"


repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web") // REST API 개발용
    implementation("org.springframework.boot:spring-boot-starter-validation") // 데이터 검증
    implementation("org.springframework.boot:spring-boot-starter-json") // JSON 처리
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    implementation("org.projectlombok:lombok") // Lombok (선택)
    annotationProcessor("org.projectlombok:lombok")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}