ARG SRCDIR=/usr/src/dajzavrsi-backend

FROM eclipse-temurin:21-jdk-jammy AS build
ARG SRCDIR
RUN mkdir -p $SRCDIR
WORKDIR $SRCDIR
COPY . $SRCDIR
RUN --mount=type=cache,target=/root/.m2 ./mvnw -f $SRCDIR/pom.xml clean install

FROM eclipse-temurin:21-jre-jammy AS runtime
ARG SRCDIR
ARG JAR_FILE=$SRCDIR/target/*.jar
COPY --from=build $JAR_FILE /app/dajzavrsi.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "/app/dajzavrsi.jar" ]
