# Price Calculator

## Overview
The Price Calculator is a simple Java project designed to calculate prices for various products based on different discount policies. This project aims to provide a flexible and easy-to-use tool for businesses to determine the final prices of their products, taking into account different discount scenarios.

### Assumptions
- Policies apply to all products
- The api returns total price for the order after discount
- Policies work as documented below

### Api
Please refer to [openapi](openapi.yml) for the API documentation.

### Discount policies
There are 2 discount policies available - `COUNT_BASED` and `PERCENTAGE_BASED`. Both are configurable within the [application.yml](/src/main/resources/application.yml).

The pricing system allows you to configure different discount policies based on specified quantity ranges. There are two types of discount policies: `COUNT_BASED` and `PERCENTAGE_BASED`. Each policy type can have multiple ranges with associated discounts.

#### Configuration format
The configuration is written in YAML format and consists of two main sections: `COUNT_BASED` and `PERCENTAGE_BASED`. Each section contains a list of range configurations with specified from, to-exclusive, and discount values.

- from: The starting quantity for the range (inclusive).
- to-exclusive: The ending quantity for the range (exclusive).
- discount: The discount applied to the price. For `COUNT_BASED`, it is the discount price for a single item. For `PERCENTAGE_BASED`, it is the percentage of discount for the total price.

```yml
    COUNT_BASED:
      - from: '100'
        to-exclusive: '1000'
        discount: 1 # Discount price for a single item - for example if product costs 5 PLN its price will be reduced to 4 PLN
    PERCENTAGE_BASED:
      - from: '100'
        to-exclusive: '1000'
        discount: 0.2 # Percentage of discount for the total price - 0.2 means 20% discount
```

In this example, there are one range for each type of policy. The `COUNT_BASED` policy provides a discount of 1 unit for each item when the quantity is between 100 (inclusive) and 1000 (exclusive). The `PERCENTAGE_BASED` policy offers a 20% discount for the total price within the same quantity range.

### Building the project
To build the project run the `./gradlew build` command

### Running the project
Please be sure to build the project first.
The easiest way to run the project is to use the docker compose.
Run `docker-compose up` to start the project (requires Docker engine). By default, the application is available on port 8080.