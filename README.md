# Toy Details Service

Toy Details Service provides the status of toy or List of toy status and get the list of toy details and filter based on price range. 

Provides APIs for querying and selecting
- List of Toy Details
- List of Toy Details Filtered on Price Range
- List of Toy Status Filtered by Filter Status
- Get Toy Status Filtered By ToyId

### Downstream

- Config DB
- Inventory - https://inventory.kidzoo.com/v2/inventory/

## Endpoints
-http://localhost:8080/toy-details/

  - GET __/toy-details/v1/find-Toys-By-Status__ - This API will retrieve list of toy when queried with status
  - GET __/toy-details/v1/get-list-of-toys__ - This API will retrieve list of all toys from database
  - GET __/toy-details/v1/get-list-of-toys/byPrice__ - This API will retrieve list of all toys with the price range from database
  - GET __/toy-details/v1/get-status-by-toyId/{toyId}__ - This API will retrieve status of toy

## General

http://localhost:8080/toy-details-cart/swagger-ui/index.html#/ - Swagger docs
(API-DOCS path: /v2/api-docs)

### Prerequisites
- Java
- Maven
- Lombok


### Build

The following commands will build and run the test:

```BASH
mvn verify
```

to deploy you can use Spring STS or the following command:

```BASH
mvn verify spring-boot:run
```