FROM openjdk:8
ADD build/libs/levonke-1.0.0-SNAPSHOT.jar levonke-Supply.jar
EXPOSE 8444
ENTRYPOINT ["java", "-jar", "levonke-Supply.jar"]
