
#  Automation API kafka dan api normal (katalon)

This repository contains **API automation tests and kafka automation** built with [Katalon Studio](https://katalon.com/) and **kafka**.

---

## Requirements

Make sure you have the following installed:

- **Katalon Studio** `v10.1.0+`
- **Java version** `v17.0.16+`
- **kafka** `v7.5.1+`


## Running Tests

Check Kafka Topic: 
```bash
docker exec -it kafka-service-kafka-1 k --list --bootstrap-server localhost:9092
```
Check Consumer Kafka Topic:
```bash
docker exec -it kafka-service-kafka-1 kafka-console-consumer --topic user-topic --from-beginning --bootstrap-server localhost:9092
```
Run Katalon:
```bash
Click Play in Test Suite using Kafka (Chrome Headless)

Click Play in Test Suite tanpa Kafka (Chrome Headless)
```
