FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml ./
COPY common-security/pom.xml common-security/pom.xml
COPY gateway/pom.xml gateway/pom.xml
COPY services/users/pom.xml services/users/pom.xml
COPY services/products/pom.xml services/products/pom.xml
COPY services/orders/pom.xml services/orders/pom.xml
COPY services/payments/pom.xml services/payments/pom.xml
COPY services/offers/pom.xml services/offers/pom.xml
COPY services/cart/pom.xml services/cart/pom.xml

COPY common-security/src common-security/src
COPY gateway/src gateway/src
COPY services/users/src services/users/src
COPY services/products/src services/products/src
COPY services/orders/src services/orders/src
COPY services/payments/src services/payments/src
COPY services/offers/src services/offers/src
COPY services/cart/src services/cart/src

RUN mvn -q -DskipTests package -pl services/users -am

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/services/users/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
