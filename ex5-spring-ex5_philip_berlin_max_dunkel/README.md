## Authors
* Name: Philip Berlin  Email: philipbe@edu.hac.ac.il
* Name: Maxim Dunkel Email: maximdu@edu.hac.ac.il


# ex5 - User Registration System with Admin Backend

ex5 is a user registration system with an admin backend that provides functionality to delete/enable/disable user accounts, reset passwords.

## Description

The ex5 application is built using Spring Boot and provides a web-based user interface for user registration and authentication. It also includes an admin backend that allows authorized users to manage user accounts and perform administrative actions.

### General Information

This application is developed using Java and utilizes the Spring Boot framework. It uses a relational database (MySQL or H2) to store user information and provides a user-friendly interface for managing user accounts.

### Functionality

The ex5 application offers the following functionality:

- User Registration: Allows users to create an account by providing their email address, username, and password.
- User Login: Provides a login form for users to enter their credentials and authenticate themselves.
- Admin Backend: Includes an admin interface to manage user accounts.
    - Enable/Disable User Accounts: Allows the admin to enable or disable user accounts.
    - Password Reset: Provides a feature to reset passwords for user accounts.

## Installation

Before running the ex5 application, ensure you have the following prerequisites:

- Java Development Kit (JDK) 17 
- Maven 3.8.4
- MySQL 8.0 (if using MySQL) or H2 (if using H2)

To install and run the ex5 application, follow these steps:

1. Clone the repository

2. Navigate to the project directory: cd ...ex5

3. Configure the database:

    - If using MySQL:
        - Create a new MySQL database name it ex5.
    


4. Build and Run the application: by clicking the Run button on the compiler

5. Access the application:

Open a web browser and navigate to `http://localhost:8080` to access the application.


## Additional information

In order create admin user, go to db and change the admin parameter of any user from 0(default) to 1.



