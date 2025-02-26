# OpenJDK 17을 베이스로 사용하는 Alpine 이미지를 사용 (필요에 따라 버전 조정)
FROM openjdk:17-jdk-alpine

# 애플리케이션 JAR 파일을 저장할 작업 디렉토리 설정
WORKDIR /app

# 애플리케이션 JAR 파일을 컨테이너 내부로 복사
# target 디렉토리에서 빌드된 JAR 파일을 복사합니다.
COPY target/*.jar app.jar

# 컨테이너의 /tmp 폴더를 VOLUME으로 설정 (스프링부트에서 임시 파일 저장용)
VOLUME /tmp

# 애플리케이션 실행: JAR 파일을 실행하여 스프링부트 애플리케이션을 시작합니다.
ENTRYPOINT ["java", "-jar", "/app.jar"]
