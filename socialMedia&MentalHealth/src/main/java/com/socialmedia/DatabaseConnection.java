package com.socialmedia;

import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/social_media_mental_health";
    private static final String USER = "root";
    private static final String PASSWORD = "1GOTarmy!"; // Add your password here if you have one

    public static Connection getConnection() throws SQLException {
        try {
            // Register MySQL Driver
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            
            // Create connection with credentials
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connected successfully!");
            return conn;
            
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
            throw e;
        }
    }
}