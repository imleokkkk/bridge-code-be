# Build 스테이지: Gradle 이미지(예: OpenJDK 17가 포함된 Gradle)를 사용하여 애플리케이션 빌드
FROM gradle:7.6.0-jdk17 as builder

# 작업 디렉토리를 설정하고, 실제 프로젝트 폴더로 이동
WORKDIR /home/gradle/project

# 리포지터리 루트의 bridge-code-be/bridge-code-be 폴더 전체를 복사
COPY bridge-code-be/ . 

# gradlew가 실행 권한이 없을 경우 권한 부여 (필요 시)
RUN chmod +x gradlew

# Gradle 빌드 실행 – bootJar 명령어로 실행 가능한 JAR 파일 생성
RUN ./gradlew bootJar --no-daemon

# 최종 스테이지: OpenJDK를 사용하여 빌드된 JAR 파일 실행
FROM openjdk:17-jdk-alpine

# 컨테이너 내부 작업 디렉토리 설정
WORKDIR /app

# 빌드 스테이지에서 생성된 JAR 파일 복사 (일반적으로 build/libs 폴더에 생성됨)
COPY --from=builder /home/gradle/project/build/libs/*.jar app.jar

# 필요한 경우 포트 노출 (예: 기본 Spring Boot는 8080 사용)
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
