# Online Library Management System

## Project Overview

The Online Library Management System is a Java-based desktop application developed using Java Swing and MySQL.

The system helps manage books, users, and book transactions through a simple graphical user interface.

## Features

* User Login
* Dashboard
* Manage Books

  * Add books
  * View books
  * Update book quantity
  * Delete books
* Manage Users

  * View users
  * Add users
* Issue Books
* Return Books
* MySQL database storage
* JDBC database connectivity
* Transaction handling
* Multithreading and synchronization

## Technologies Used

* Java
* Java Swing
* MySQL
* JDBC
* VS Code
* MySQL Connector/J

## Project Structure

```text
Online Library Management System
│
├── Main.java
├── Login.java
├── Dashboard.java
│
├── Book.java
├── BookDAO.java
├── BookWindow.java
│
├── User.java
├── UserDAO.java
├── UserWindow.java
│
├── TransactionDAO.java
├── IssueBook.java
├── ReturnBook.java
│
├── LibraryOperations.java
├── DatabaseConnection.java
│
├── README.md
└── .gitignore
```

## Database

The project uses a MySQL database named:

```text
library_management
```

Main tables:

* `books`
* `users`
* `transactions`

## Requirements

Before running the project, install:

1. Java JDK
2. MySQL Server
3. MySQL Connector/J
4. VS Code or another Java IDE

## Database Configuration

Database configuration is stored separately in:

```text
db.properties
```

This file contains the local database connection details and should not be uploaded to GitHub.

## How to Run

Compile the project using:

```text
javac -cp ".;libb\mysql-connector-j-26.7.0.jar" *.java
```

Run the application using:

```text
java -cp ".;libb\mysql-connector-j-26.7.0.jar" Main
```

The application starts with the Login screen.

## Application Workflow

```text
Login
  ↓
Dashboard
  ↓
Manage Books
  ├── Add Book
  ├── View Books
  ├── Update Book
  └── Delete Book

Dashboard
  ↓
Users
  └── Add/View Users

Dashboard
  ↓
Issue Book
  ↓
Transaction stored in database
  ↓
Book quantity updated

Dashboard
  ↓
Return Book
  ↓
Transaction updated
  ↓
Book quantity restored
```

## OOP Concepts Used

The project demonstrates:

* Classes and Objects
* Encapsulation
* Inheritance
* Interfaces
* Polymorphism
* Exception Handling

## Collections and Generics

The project uses Java Collections and Generics, including:

```java
List<Book>
List<User>
ArrayList<Book>
ArrayList<User>
```

## Multithreading and Synchronization

The Issue Book operation uses a separate thread for the database operation.

Synchronization is used to protect the book issuing operation from simultaneous access.

## Database Operations

Database operations are separated from the GUI using DAO classes:

* `BookDAO`
* `UserDAO`
* `TransactionDAO`

This keeps the application modular and easier to maintain.

## Project Purpose

The main purpose of this project is to demonstrate Java programming concepts, GUI development, object-oriented programming, database connectivity, and basic software architecture through a practical library management application.
