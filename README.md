# Petstore API BDD Automation Framework

## Project Overview

The **Petstore API BDD Automation Framework** is a hybrid API automation framework developed using Java, REST Assured, Cucumber, TestNG, and Maven.

The framework automates the Petstore REST APIs and validates API functionality, response status codes, data consistency, and negative scenarios.

The project follows the **Behavior-Driven Development (BDD)** approach using Cucumber and Gherkin. It is designed with reusable components, separation of concerns, and maintainable architecture.

**Application Under Test:** [Swagger Petstore](https://petstore.swagger.io/)

**Base URL:** `https://petstore.swagger.io/v2`

## Technology Stack

| Technology | Purpose |
|---|---|
| Java 21 | Programming language |
| REST Assured | REST API automation |
| Cucumber | BDD implementation |
| Gherkin | Writing readable test scenarios |
| TestNG | Test execution and assertions |
| Maven | Build and dependency management |
| Log4j2 | Execution logging |
| Jackson | JSON serialization and deserialization |
| Allure | Test reporting |
| Postman | Manual API testing and request validation |
| Git & GitHub | Version control |
| Eclipse IDE | Development environment |

## Project Architecture

The framework follows a layered architecture to improve reusability and maintainability.

```text
Precision-Api-Bdd-automation-framework
│
├── src/main/java
│   │
│   ├── builder
│   │   └── RequestBuilder.java
│   │
│   ├── clients
│   │   ├── PetApiClient.java
│   │   ├── StoreApiClient.java
│   │   └── UserApiClient.java
│   │
│   ├── constants
│   │   └── ApiEndpoints.java
│   │
│   ├── payloads
│   │   ├── Pet.java
│   │   └── Category.java
│   │
│   ├── utils
│   │   ├── ConfigReader.java
│   │   ├── PetsFactory.java
│   │   └── LogsFactory.java
│   │
│   └── validators
│       └── ResponseValidator.java
│
├── src/test/java
│   │
│   ├── hooks
│   ├── runners
│   └── stepdefinitions
│
├── src/test/resources
│   │
│   ├── features
│   │   ├── Pet_lifecycle.feature
│   │   ├── Pet_Inventory.feature
│   │   ├── User_Security.feature
│   │   └── data-consistancy.feature
│   │
│   ├── schemas
│   ├── postman
│   ├── allure.properties
│   └── log4j2.xml
│
├── pom.xml
└── README.md
```

*Note: The structure above describes the main framework components. Keep the file and folder names aligned with the actual repository when committing this README.*

## Framework Component Responsibilities

| Component | Responsibility |
|---|---|
| `RequestBuilder` | Builds reusable REST Assured requests with common configuration |
| `PetApiClient` | Handles Pet API operations such as POST, GET, PUT, DELETE, and status-based retrieval |
| `StoreApiClient` | Handles store inventory API requests |
| `UserApiClient` | Handles user creation, retrieval, and login requests |
| `ApiEndpoints` | Stores API endpoint constants |
| `Pet` | POJO used for pet request and response data |
| `Category` | Represents pet category details |
| `PetsFactory` | Generates dynamic pet test data and updates pet status |
| `ConfigReader` | Reads configuration properties such as the base URL |
| `LogsFactory` | Provides Log4j2 logger instances |
| `ResponseValidator` | Centralizes response status and response data validations |
| `Step Definitions` | Connects Gherkin steps with Java automation methods |
| `Feature Files` | Describe API test scenarios in readable Gherkin syntax |
| `Hooks` | Supports common test setup and teardown operations |
| `Test Runner` | Executes Cucumber scenarios using TestNG |

## Automated API Test Scenarios

### Test Case 1: Pet Lifecycle – CRUD Operations

**Objective:** Validate the complete lifecycle of a pet using CRUD operations.

**Execution flow:**

1. Create a new pet using `POST /pet`.
2. Generate a unique pet name and set its initial status to `available`.
3. Extract the pet ID from the POST response.
4. Retrieve the pet using `GET /pet/{petId}`.
5. Validate that the retrieved name and status match the original request.
6. Update the pet status to `sold` using `PUT /pet`.
7. Delete the pet using `DELETE /pet/{petId}`.
8. Verify the delete response status code is 200.
9. Perform another GET request and verify that the response status is 404.

**Key concepts:** CRUD operations, dynamic test data, response extraction, request chaining, and response validation.

### Test Case 2: Inventory Analysis

**Objective:** Compare the available pet count from two different API endpoints.

**Execution flow:**

1. Retrieve the inventory using `GET /store/inventory`.
2. Extract the `available` count from the inventory response.
3. Retrieve all available pets using `GET /pet/findByStatus?status=available`.
4. Count the number of pets returned in the response.
5. Compare the available pet count with the inventory count.

**Key concepts:** JSON parsing, response extraction, collection handling, and cross-endpoint validation.

### Test Case 3: User Security and Error Handling

**Objective:** Validate user-related API behavior and negative scenarios.

**Execution flow:**

1. Submit a POST request to `/user` using user details containing an invalid email format.
2. Retrieve a nonexistent user using `GET /user/nonExistentUser123`.
3. Validate the 404 response status code.
4. Validate that the response message contains `User not found`.
5. Perform a GET request to `/user/login` using invalid credentials.
6. Validate the login response status and response structure.

**Key concepts:** Negative testing, error handling, response validation, and invalid input testing.

### Test Case 4: Cross-Endpoint Data Consistency

**Objective:** Verify that pet data remains consistent across multiple API endpoints.

**Execution flow:**

1. Create a pet using `POST /pet` with a specific category name, such as `HighValue Bulldog`.
2. Set its initial status to `available`.
3. Extract the created pet ID.
4. Update the pet status to `sold` using `PUT /pet`.
5. Retrieve the store inventory using `GET /store/inventory`.
6. Retrieve all sold pets using `GET /pet/findByStatus?status=sold`.
7. Use Java collection processing to verify that the created pet ID exists in the retrieved sold pets list.

**Key concepts:** Dynamic test data, category-based payloads, Java collections, and cross-endpoint data consistency.

## Postman Collection

A Postman collection is maintained for manual API testing and request validation.

### Assignment 1: Request Building and Basic Navigation

- Created a GET request for `/pet/findByStatus`.
- Added the query parameter `status=available`.
- Added a test assertion to validate HTTP status code `200`.

### Assignment 2: Working with Request Body

- Created a POST request for `/pet`.
- Selected Body → raw → JSON.
- Added a pet payload containing ID, name, and status.
- Added a response assertion to verify that the response contains the created pet name.

### Assignment 3: Understanding Status Codes

Covered the following requests:

| Scenario | Expected Result |
|---|---|
| Valid GET request | 200 OK |
| GET request for nonexistent pet | 404 Not Found |
| POST request with empty body | 400 Bad Request or 405 Method Not Allowed |

### Assignment 4: Environment Variables

- Created a Postman environment named `Petstore Environment`.
- Added the variable `url`.
- Configured its value as `https://petstore.swagger.io/v2`.
- Updated collection request URLs to use the environment variable.

Example:

```text
{{url}}/pet/123
```

This avoids repeating the base URL in individual requests.

## Configuration

The base URL and other configuration values are maintained in `config.properties`.

Example:

```properties
baseUrl=https://petstore.swagger.io/v2
connectionTimeout=10000
socketTimeout=10000
```

## Execution Instructions

### Prerequisites

- Java JDK 21
- Maven
- Git
- Eclipse or IntelliJ IDEA
- Internet connection

### Clone the Repository

```bash
git clone <YOUR_GITHUB_REPOSITORY_URL>
```

### Navigate to the Project

```bash
cd Precision-Api-Bdd-automation-framework
```

### Execute the Tests

```bash
mvn clean test
```

This command cleans the previous build, compiles the project, and executes the configured test suite.

## Reports and Logging



### Allure Reports

Allure results are configured to be generated in:

```text
target/allure-results/
```

To view the report, if Allure CLI is installed:

```bash
allure serve target/allure-results
```

### Logging

Log4j2 is used to maintain execution logs and help identify failures during API execution.

Configuration:

```text
src/test/resources/log4j2.xml
```

## Validation and Assertions

The framework uses a centralized `ResponseValidator` class to maintain reusable assertions.

Examples of validations include:

- HTTP status code validation.
- Response body content validation.
- Expected error message validation.
- Pet ID existence validation in retrieved API responses.
- Login response status and structure validation.

This approach avoids duplicating assertion logic across step definitions.

## Version Control

Git is used to maintain the project source code and track development changes.

GitHub Repository: **https://github.com/ramyasada/Precision-bdd-api-automation-framework**
## Key Learning Outcomes

- Designing a reusable API automation framework.
- Implementing BDD using Cucumber and Gherkin.
- Automating REST APIs using REST Assured.
- Working with POJOs and dynamic request payloads.
- Implementing API request chaining.
- Performing cross-endpoint data validation.
- Parsing JSON responses and handling collections.
- Managing dependencies and execution through Maven.
- Using Git for version control.
- Implementing logging and test reporting.
- Creating and validating API requests using Postman.

---

**Project:** Petstore API BDD Automation Framework  
**Domain:** API Automation Testing  
**Application:** Swagger Petstore  
**Base URL:** `https://petstore.swagger.io/v2`
