# Design Software Architecture Research #


The purpose of this repository is to research and compare different technologies by implementing the same user requirements with architecture designed for production ready web application.

Deployed up and running MyBooks service is [here](https://mybookscloud.herokuapp.com/).

The most recent source of implementation is [here](https://github.com/mstane/software-architecture-lab/tree/master/mybooks3).

Wiki pages about the project are [here](https://github.com/mstane/software-architecture-lab/wiki).

## Application Architecture supports ##

 - Security
   - Authentication
   - Authorization
     - URL-based
     - Method-level security
   - HTTPS
 - Database 
   - independence
   - change management
 - i18n
 - Configuration
   - Externalize
 - Server independence
 - IDE independence
 - Responsive UI
 - Scalability
 - Caching
 - Logging
 - Validation
   - Client
   - Server
 - Pagination
 - Tests
   - Unit
   - Integration
   - Performance
 - Naming conventions
 - Best recommendation and proven solution


## User requirements ##
 
They are simplified to the level to have point but not to overwhelm the main goal which is researching technology stack and its implementation.

This web application keeps track of your read books. 
 - You can register yourself, create and update data about the book and to record your notes or quotations. You can update your profile and search your completely content on the site.
 - Apart for common use, there is also administration part which has all of these functionalities and have ability to administrate other users.

Details of requirements are in [Product Requirements Document] (https://github.com/mstane/lab/wiki/Product-Requirements-Document)

## Technologies ##

 - [MyBooks](https://github.com/mstane/software-architecture-lab/tree/master/mybooks): GWT, MGWT, MVP, Guice, GIN, JPA, EclipseLink, EasyMock, Maven
 - [MyBooks2](https://github.com/mstane/software-architecture-lab/tree/master/mybooks2): Spring MVC, JSP, JPA, Hibernate, Maven
 - [MyBooks3](https://github.com/mstane/software-architecture-lab/tree/master/mybooks3): Spring MVC, Spring Boot, JPA, Hibernate, AngularJS, Bootstrap, Maven


 