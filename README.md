# Coin-Backend
Coin is a personal back-end microservices project that allows users to track their spending to help achieve financial stability.
## User Features
* Sign up and log into accounts with email and password.
* Expenses lists:
  * Create and delete lists to track personal expenses by field (Food, shopping, etc.).
  * See the total cost of each list items.
* Expense items:
  * Create and delete individual items for each list.
  * Assign a cost to each item.
### Future Features
* Edit lists and items names.
* Add a description to each list.
* Change each list currency in real time.
* Give the user the option to view expense lists on a weekly, monthly, or yearly basis.
* Display summarized information such as total expenses, average spending, and trends for each time period.
## Technologies
This project was developed in Java with the Spring Boot and Spring Cloud frameworks. It consists of multiple microservices, each with a specific role to play in the system.
### Project Services:
![Project Service Diagram](https://github.com/TomyAlberdi/Coin-Backend/assets/66546046/98efc41c-b1a7-4ef7-a479-4cf7aa8c74d9)
* **Bussiness services**:
  * **ApiUser**: Manages user endpoints.
  * **ApiList**: Manages list endpoints.
    * **Feign Item Client**: Allows the List microservice to communicate and exchange information with the Item microservice.
    * **Circuit Breaker**: Provides a layer of resilience to the List microservice in case the Item microservice fails.
  * **ApiItem**: Manages item endpoints.
* **Externalized Configuration**:
  * **GitHub Config**: ![GitHub repository](https://github.com/TomyAlberdi/Coin-Config) that hosts and centralices services configuration for remote access. 
  * **Config Server**: Searches and distributes the configuration to each service.
* **Eureka Server**: Allows for service discovery. Keeps track of each instance of services.
* **Gateway**: Provides a central port for all instances of other microservices to be accessed through, and will allow 
