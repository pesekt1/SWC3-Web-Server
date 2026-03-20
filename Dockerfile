# Dockerfile
ARG GIT_BRANCH=unknown
ARG GIT_COMMIT=unknown

FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY pom.xml .
COPY src ./src
RUN mvn -B -DskipTests clean package

FROM eclipse-temurin:17-jre
WORKDIR /app
LABEL git.branch=$GIT_BRANCH git.commit=$GIT_COMMIT
COPY --from=build /workspace/target/*.jar ./app.jar
EXPOSE 5557
ENTRYPOINT ["java","-jar","/app/app.jar"]

