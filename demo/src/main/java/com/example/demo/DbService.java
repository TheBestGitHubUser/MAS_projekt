package com.example.demo;

import java.sql.*;


public class DbService {

    public static int CLIENT_ID = 1;
    public static int BUSINESSPERSON_ID = 1;


    public static void dropTables(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE SiteUser");
            statement.executeUpdate("DROP TABLE Client");
            statement.executeUpdate("DROP TABLE Businessperson");
            statement.executeUpdate("DROP TABLE Employee");
            statement.executeUpdate("DROP TABLE Event");
            statement.executeUpdate("DROP TABLE Participation");
            statement.executeUpdate("DROP TABLE Product");
            statement.executeUpdate("DROP TABLE OrderProduct");
            statement.executeUpdate("DROP TABLE Review");
            statement.executeUpdate("DROP TABLE Article");
            statement.executeUpdate("DROP TABLE Comment");

            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void create(Connection connection) {
        try (Statement statement = connection.createStatement()) {


            statement.execute("CREATE TABLE SiteUser (" +
                    "ID INT PRIMARY KEY, " +
                    "Name VARCHAR(20), " +
                    "Email VARCHAR(30), " +
                    "Password VARCHAR(20))");

            statement.execute("CREATE TABLE Client (" +
                    "ID INT PRIMARY KEY, " +
                    "UserID INT, " +
                    "BirthDate DATE)");


            statement.execute("CREATE TABLE Businessperson (" +
                    "ID INT PRIMARY KEY, " +
                    "UserID INT, " +
                    "Role VARCHAR(20), "+
                    "NIPnumber INT, " +
                    "Rating FLOAT, "+
                    "AverageAttendance FLOAT)");

            statement.execute("CREATE TABLE Employee (" +
                    "ID INT PRIMARY KEY, " +
                    "UserID INT, " +
                    "Position VARCHAR(20), "+
                    "HireDate date, "+
                    "Salary float)");

            statement.execute("CREATE TABLE Product (" +
                    "ID INT PRIMARY KEY generated always as identity (start with 1 increment by 1), " +
                    "BusinessPersonID INT, " +
                    "Name VARCHAR(20),"+
                    "Description VARCHAR(50), "+
                    "Price FLOAT, " +
                    "Stock INT, "+
                    "Category VARCHAR(20), "+
                    "ImageURL VARCHAR(100))");

            statement.execute("CREATE TABLE OrderProduct (" +
                    "ID INT PRIMARY KEY generated always as identity (start with 1 increment by 1), " +
                    "ClientID INT, " +
                    "ProductID INT, "+
                    "OrderDate date, "+
                    "Status VARCHAR(20))");

            statement.execute("CREATE TABLE Review (" +
                    "ID INT PRIMARY KEY generated always as identity (start with 1 increment by 1), " +
                    "OrderID INT, " +
                    "Comment VARCHAR(50))");

            statement.execute("CREATE TABLE Event (" +
                    "ID INT PRIMARY KEY, " +
                    "BusinessPersonID INT, "+
                    "Description VARCHAR(50),"+
                    "DateTime date, "+
                    "Location VARCHAR(20)," +
                    "Capacity INT)");

            statement.execute("CREATE TABLE Participation (" +
                    "ID INT PRIMARY KEY, " +
                    "ClientID INT,"+
                    "EventID INT, " +
                    "Placement INT)");

            statement.execute("CREATE TABLE Article (" +
                    "ID INT PRIMARY KEY, " +
                    "EmployeeID INT, " +
                    "Title VARCHAR(50), "+
                    "Content VARCHAR(100), "+
                    "PublishDate DATE, " +
                    "Views INT)");

            statement.execute("CREATE TABLE Comment (" +
                    "ID INT PRIMARY KEY, " +
                    "ClientID INT, " +
                    "ArticleID INT, "+
                    "Content VARCHAR(100), "+
                    "PostDate date)");



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    public static void add(Connection connection){
        try (Statement statement = connection.createStatement()) {
            // User
            statement.executeUpdate("INSERT INTO SiteUser VALUES (1, 'Jan Kowalski', 'jan@example.com', 'pass123')");
            statement.executeUpdate("INSERT INTO SiteUser VALUES (2, 'Anna Nowak', 'anna@example.com', 'abc123')");
            statement.executeUpdate("INSERT INTO SiteUser VALUES (3, 'Ocun', 'biz@example.com', 'bizpass')");
            statement.executeUpdate("INSERT INTO SiteUser VALUES (4, 'La sportiva', 'LaSportiva@example.com', 'haslo123')");
            statement.executeUpdate("INSERT INTO SiteUser VALUES (5, 'evolv', 'evolv@example.com', 'password')");
            statement.executeUpdate("INSERT INTO SiteUser VALUES (6, 'La sportiva', 'methodS@example.com', 'BruhBruhBruh')");

            // Client
            statement.executeUpdate("INSERT INTO Client VALUES (1, 1, DATE('1990-05-12'))");
            statement.executeUpdate("INSERT INTO Client VALUES (2, 2, DATE('1995-09-01'))");

            // Businessperson
            statement.executeUpdate("INSERT INTO Businessperson VALUES (1, 3, 'Seller', 123456789, 4.5, 0)");
            statement.executeUpdate("INSERT INTO Businessperson VALUES (2, 4, 'Seller', 123456781, 4.2, 0)");
            statement.executeUpdate("INSERT INTO Businessperson VALUES (3, 5, 'Seller', 154356781, 4.7, 0)");
            statement.executeUpdate("INSERT INTO Businessperson VALUES (4, 6, 'Seller', 1289512781, 4.9, 0)");

            // Employee
            statement.executeUpdate("INSERT INTO Employee VALUES (1, 4, 'Redaktor', DATE('2020-01-15'), 4000)");

            // Product
            statement.executeUpdate("INSERT INTO Product (BusinessPersonID, Name, Description, Price ,Stock, Category, ImageURL) " +
                    "VALUES ( 1, 'Ocune Ozone','dobre buty', 549.99, 10, 'Buty', " +
                    "'https://multanex.pl/userdata/public/gfx/54985/0i3tu1ftd2.04700-Ozone-red-1.jpg')");

            statement.executeUpdate("INSERT INTO Product (BusinessPersonID, Name, Description, Price ,Stock, Category, ImageURL)" +
                    " VALUES ( 1, 'Ocuna Anthea','dobry uprzaz', 349.99, 5, 'Sprzet', " +
                    "'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTV6-hHFQlswyDCOpe0vNR4kxoIM7-EHIf1og&s')");

            statement.executeUpdate("INSERT INTO Product (BusinessPersonID, Name, Description, Price ,Stock, Category, ImageURL) " +
                    "VALUES ( 2, 'LS solution','tuptuptuptup wspinanie', 699.99, 2, 'Buty', " +
                    "'https://www.northernrocks.co.nz/wp-content/uploads/2021/09/Solution-1-Lee.jpg')");

            statement.executeUpdate("INSERT INTO Product (BusinessPersonID, Name, Description, Price ,Stock, Category, ImageURL)" +
                    " VALUES ( 2, 'LS Theory','buty buty buty buty buty', 749.99, 9, 'buty', " +
                    "'https://www.northernrocks.co.nz/wp-content/uploads/2021/09/20G_000100_02.jpg')");

            // Order
            statement.executeUpdate("INSERT INTO OrderProduct (ClientID, ProductID, OrderDate, Status) VALUES (1, 1, DATE('2025-05-01'), 'In process')");
            statement.executeUpdate("INSERT INTO OrderProduct (ClientID, ProductID, OrderDate, Status) VALUES (2, 3, DATE('2025-05-10'), 'Completed')");
            statement.executeUpdate("INSERT INTO OrderProduct (ClientID, ProductID, OrderDate, Status) VALUES (1, 4, DATE('2025-05-11'), 'Completed')");
            // Review
            statement.executeUpdate("INSERT INTO Review (OrderID, Comment) VALUES ( 2, 'Super jakość!')");
            statement.executeUpdate("INSERT INTO Review (OrderID, Comment) VALUES ( 3, 'znowu z tym pasztetem')");

            // Event
            statement.executeUpdate("INSERT INTO Event VALUES (1, 1, 'Szkolenie z asekuracji', DATE('2025-06-20'), 'Kraków', 20)");
            statement.executeUpdate("INSERT INTO Event VALUES (2, 1, 'Wspinaczka w skałach', DATE('2025-07-15'), 'Jura', 15)");

            // Participation
            statement.executeUpdate("INSERT INTO Participation VALUES (1, 1, 1, 3)");
            statement.executeUpdate("INSERT INTO Participation VALUES (2, 2, 2, 1)");

            // Article
            statement.executeUpdate("INSERT INTO Article VALUES (1, 1, 'Bezpieczeństwo w górach', 'Artykuł o asekuracji i przygotowaniu.', DATE('2025-03-15'), 120)");
            statement.executeUpdate("INSERT INTO Article VALUES (2, 1, 'Nowy sprzęt 2025', 'Testy sprzętu wspinaczkowego.', DATE('2025-04-10'), 80)");

            // Comment
            statement.executeUpdate("INSERT INTO Comment VALUES (1, 1, 1, 'Świetny artykuł!', DATE('2025-03-16'))");
            statement.executeUpdate("INSERT INTO Comment VALUES (2, 2, 2, 'Dobre porównania sprzętu', DATE('2025-04-12'))");

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
}

