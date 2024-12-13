package com.socialmedia;

import java.sql.*;
import java.util.Scanner;

public class SocialMediaAnalytics {
    private Connection connection;
    
    public SocialMediaAnalytics() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }
    
    public void displayMenu() {
        System.out.println("\n=== Social Media and Mental Health Analysis ===");
        System.out.println("Choose an analysis to view:");
        System.out.println("\n1. Platform Usage Analysis");
        System.out.println("   (See which social media platforms are most commonly used)");
        
        System.out.println("\n2. Time Spent Analysis");
        System.out.println("   (Discover average time users spend on different platforms)");
        
        System.out.println("\n3. Emotional State Distribution");
        System.out.println("   (Understand the distribution of user emotional states)");
        
        System.out.println("\n4. User Engagement Analysis");
        System.out.println("   (View patterns of posts, likes, and comments across platforms)");
        
        System.out.println("\n5. Most Active Users");
        System.out.println("   (See details about our most engaged users)");
        
        System.out.println("\n6. Test Database Connection");
        System.out.println("   (Verify connection to the database)");
        
        System.out.println("\n7. Exit Application");
        
        System.out.print("\nEnter your choice (1-7): ");
    }
    
    public void showPlatformPopularity() throws SQLException {
        String query = """
            SELECT 
                p.platform_name,
                COUNT(sma.accountID) as user_count
            FROM Platform p
            LEFT JOIN Social_Media_Account sma ON p.platformID = sma.platformID
            GROUP BY p.platform_name
            ORDER BY user_count DESC
        """;
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            System.out.println("\n=== Platform Popularity Analysis ===");
            
            while (rs.next()) {
                System.out.printf("The platform %s has %d users, making it %s.\n", 
                    rs.getString("platform_name"),
                    rs.getInt("user_count"),
                    rs.isFirst() ? "the most popular platform" : "another significant platform");
            }
        }
    }
    
    public void showAverageUsageTime() throws SQLException {
        String query = """
            SELECT 
                p.platform_name,
                ROUND(AVG(i.Daily_Usage_Minutes), 2) as avg_daily_minutes
            FROM Platform p
            JOIN Social_Media_Account sma ON p.platformID = sma.platformID
            JOIN Interactions i ON sma.accountID = i.account_id
            GROUP BY p.platform_name
            ORDER BY avg_daily_minutes DESC
        """;
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            System.out.println("\n=== Daily Usage Time Analysis ===");
            
            while (rs.next()) {
                System.out.printf("Users spend an average of %.1f minutes per day on %s.\n",
                    rs.getDouble("avg_daily_minutes"),
                    rs.getString("platform_name"));
            }
        }
    }
    
    public void showEmotionalDistribution() throws SQLException {
        String query = """
            SELECT 
                dominant_emotion,
                COUNT(*) as emotion_count,
                ROUND((COUNT(*) * 100.0 / (SELECT COUNT(*) FROM Emotional_Status)), 2) as percentage
            FROM Emotional_Status
            GROUP BY dominant_emotion
            ORDER BY emotion_count DESC
        """;
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            System.out.println("\n=== Emotional State Analysis ===");
            
            while (rs.next()) {
                System.out.printf("Among the users, %.2f%% report feeling %s, with %d users in this emotional state.\n",
                    rs.getDouble("percentage"),
                    rs.getString("dominant_emotion").toLowerCase(),
                    rs.getInt("emotion_count"));
            }
        }
    }
    
    public void showPlatformEngagement() throws SQLException {
        String query = """
            SELECT 
                p.platform_name,
                ROUND(AVG(i.Posts_Per_Day), 2) as avg_posts,
                ROUND(AVG(i.Likes_Received_Per_Day), 2) as avg_likes,
                ROUND(AVG(i.Comments_Received_Per_Day), 2) as avg_comments
            FROM Platform p
            JOIN Social_Media_Account sma ON p.platformID = sma.platformID
            JOIN Interactions i ON sma.accountID = i.account_id
            GROUP BY p.platform_name
            ORDER BY avg_likes DESC
        """;
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            System.out.println("\n=== Platform Engagement Analysis ===");
            
            while (rs.next()) {
                System.out.printf("On %s, users make an average of %.1f posts per day, receiving approximately %.1f likes " +
                                "and %.1f comments per day.\n",
                    rs.getString("platform_name"),
                    rs.getDouble("avg_posts"),
                    rs.getDouble("avg_likes"),
                    rs.getDouble("avg_comments"));
            }
        }
    }
    
    public void showTopUsers() throws SQLException {
        String query = """
            SELECT 
                u.userID,
                u.gender,
                u.age,
                p.platform_name,
                i.Daily_Usage_Minutes,
                i.Likes_Received_Per_Day
            FROM User u
            JOIN Social_Media_Account sma ON u.userID = sma.userID
            JOIN Platform p ON sma.platformID = p.platformID
            JOIN Interactions i ON sma.accountID = i.account_id
            ORDER BY i.Likes_Received_Per_Day DESC
            LIMIT 5
        """;
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            System.out.println("\n=== Top User Engagement Analysis ===");
            
            while (rs.next()) {
                System.out.printf("User %d, a %d-year-old %s, spends %d minutes daily on %s and receives an average of %.1f likes per day.\n",
                    rs.getInt("userID"),
                    rs.getInt("age"),
                    rs.getString("gender").toLowerCase(),
                    rs.getInt("Daily_Usage_Minutes"),
                    rs.getString("platform_name"),
                    rs.getDouble("Likes_Received_Per_Day"));
            }
        }
    }
    
    public void testConnection() {
        try {
            Connection testConn = DatabaseConnection.getConnection();
            if (testConn != null) {
                System.out.println("\nConnection test successful! Database is accessible.");
                testConn.close();
            }
        } catch (SQLException e) {
            System.err.println("\nConnection test failed: " + e.getMessage());
        }
    }
    
    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Database connection closed successfully.");
        }
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            SocialMediaAnalytics analytics = new SocialMediaAnalytics();
            
            while (true) {
                analytics.displayMenu();
                int choice = scanner.nextInt();
                
                try {
                    switch (choice) {
                        case 1:
                            System.out.println("\nAnalyzing platform popularity trends...");
                            analytics.showPlatformPopularity();
                            break;
                        case 2:
                            System.out.println("\nCalculating average usage times...");
                            analytics.showAverageUsageTime();
                            break;
                        case 3:
                            System.out.println("\nAnalyzing emotional state distribution...");
                            analytics.showEmotionalDistribution();
                            break;
                        case 4:
                            System.out.println("\nAnalyzing platform engagement metrics...");
                            analytics.showPlatformEngagement();
                            break;
                        case 5:
                            System.out.println("\nIdentifying most active users...");
                            analytics.showTopUsers();
                            break;
                        case 6:
                            System.out.println("\nTesting database connection...");
                            analytics.testConnection();
                            break;
                        case 7:
                            System.out.println("\nThank you for using Social Media Analytics!");
                            System.out.println("Closing application...");
                            analytics.close();
                            return;
                        default:
                            System.out.println("\nInvalid choice. Please enter a number between 1 and 7.");
                    }

                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine(); // Consume newline
                    scanner.nextLine(); // Wait for user input
                    
                } catch (SQLException e) {
                    System.err.println("Error executing query: " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.err.println("Fatal error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}