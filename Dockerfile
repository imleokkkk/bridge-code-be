# 1. Build 스테이지: Gradle 이미지 사용 (JDK 17 포함)
FROM gradle:7.6.0-jdk17 as builder

# 2. 작업 디렉토리 설정
WORKDIR /home/gradle/project

# 3. Gradle 의존성 캐싱 최적화
COPY build.gradle.kts settings.gradle.kts gradle/ ./
RUN gradle dependencies --no-daemon

# 4. 전체 프로젝트 복사
COPY . .

# 5. 실행 권한 부여 (필요한 경우)
RUN chmod +x gradlew

# 6. Gradle 빌드 실행 (실행 가능한 JAR 파일 생성)
RUN ./gradlew bootJar --no-daemon

# 7. 최종 실행 환경: OpenJDK 17
FROM openjdk:17-jdk-alpine

# 8. 작업 디렉토리 설정
WORKDIR /app

# 9. 빌드된 JAR 파일 복사
COPY --from=builder /home/gradle/project/build/libs/*.jar app.jar

# 10. 컨테이너에서 사용할 포트 노출
EXPOSE 8080

# 11. 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
