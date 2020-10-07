FROM adoptopenjdk/openjdk11:latest

RUN mkdir -p /software/app

ADD build/libs/eWallet-0.0.1-SNAPSHOT.jar  /software/app/eWallet.jar

ENV port=8080

CMD java -jar /software/app/eWallet.jar