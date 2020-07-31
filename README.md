Url Shortening Service - Part 1
================================

APIs for Url shortening service. It converts long urls into short codes.
The service also provides a mechanism to get the original (long) Url when provided with the exact short Url code.
No persistence mechanism is integrated in this version.

# Requirements
### Part one - Basic local service
* A REST API that takes as input a Url and gives back a short Url
* A REST API that takes the short Url and gives back the original Url

# How to run
Run it (using `mvn spring-boot:run`) or your favorite IDE.

### Links
- Swagger (API documentation for this project is included with Swagger Configuration)
  - http://localhost:5000/swagger-ui.html
  
#### Examples
### GET http://localhost:5000/short?url=https://stash.backbase.com/projects/PO/repos/payment-order-integration-spec/browse/src/main/resources/schemas/definitions.json#38
```json
o4ISCVBiy
```

### GET http://localhost:5000/long?tiny=o4ISCVBiy
```json
http://localhost:5000/short?url=https://stash.backbase.com/projects/PO/repos/payment-order-integration-spec/browse/src/main/resources/schemas/definitions.json
```
