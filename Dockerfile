#use open jdk 17(알파인 및 어댑터 찾아봐야됨)

FROM openjdk:17

USER root

# 앱 루트 디렉토리
WORKDIR /app

# Gradle 실행에 필요한 파일 복사
COPY gradle gradle
COPY gradlew .
COPY settings.gradle .
COPY build.gradle .

# 권한 설정
RUN chmod +x ./gradlew

# 프로젝트 복사
COPY src src

# Gradle 빌드
RUN ./gradlew clean build

# Jar 파일 복사
COPY build/libs/*.jar app.jar

# Expose port
EXPOSE 8000

# 실행
CMD ["java", "-jar", "app.jar"]