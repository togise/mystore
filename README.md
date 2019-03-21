# mystore

## How To Run?
`./gradlew bootrun`

Runs on localhost:8080

## To Test Via Http
request: http://localhost:8080/product/13860428

Will return the following response
```json
{
  "currentPrice": {
    "currency": "USD",
    "price": 22.44
  },
  "id": 13860428,
  "name": "The Big Lebowski (Blu-ray)"
}
```

For all other ids requests it will return Http Status 404

## Code Structure
Code is made up of 3 sub modules
1. **product-price-repository**: Implements the repository using a local DynamoDB
2. **target-redsky-client**: implements a naming client to make calls to the redsky api
3. **web-controller** Contains the spring boot application that has the controller for the 
ProductView

## Web-Controller tests
Can be found at web-controller/src/test/groovy/com/togise/mystore/web/controller/ProductControllerTest.groovy

## References
https://github.com/redskap/aws-dynamodb-java-example-local-testing
https://guides.gradle.org/creating-multi-project-builds/
https://start.spring.io/
