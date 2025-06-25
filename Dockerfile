FROM gcr.io/distroless/java21-debian12:nonroot

ENV LANG='nb_NO.UTF-8' LC_ALL='nb_NO.UTF-8' TZ="Europe/Oslo"
ENV JDK_JAVA_OPTIONS="-XX:MaxRAMPercentage=75 -XX:ActiveProcessorCount=2"

COPY build/quarkus-app/lib/ /app/lib/
COPY build/quarkus-app/*.jar /app/app.jar
COPY build/quarkus-app/app/ /app/app/
COPY build/quarkus-app/quarkus/ /app/quarkus/

CMD ["-jar", "/app/app.jar"]