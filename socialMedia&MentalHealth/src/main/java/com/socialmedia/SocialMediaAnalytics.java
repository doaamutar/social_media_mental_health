package com.socialmedia;

// Rest of your SocialMediaAnalytics.java content...// SocialMediaAnalytics.java
import java.sql.*;
import java.util.Scanner;

public class SocialMediaAnalytics {
    private Connection connection;
    
    public SocialMediaAnalytics() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }
    
    public void displayMenu() {
        System.out.println("\n=== Social Media Analytics Menu ===");
        System.out.println("1. Platform Popularity");
        System.out.println("2. Average Usage Time by Platform");
        System.out.println("3. Emotional Distribution");
        System.out.println("4. Platform Engagement Metrics");
        System.out.println("5. Top Users");
        System.out.println("6. Test Connection");
        System.out.println("7. Exit");
        System.out.print("Enter your choice (1-7): ");
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
            
            System.out.println("\n=== Platform Popularity ===");
            System.out.printf("%-15s %s%n", "Platform", "User Count");
            System.out.println("------------------------");
            
            while (rs.next()) {
                System.out.printf("%-15s %d%n", 
                    rs.getString("platform_name"),
                    rs.getInt("user_count"));
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
            
            System.out.println("\n=== Average Usage Time by Platform ===");
            System.out.printf("%-15s %s%n", "Platform", "Avg Minutes/Day");
            System.out.println("--------------------------------");
            
            while (rs.next()) {
                System.out.printf("%-15s %.2f%n",
                    rs.getString("platform_name"),
                    rs.getDouble("avg_daily_minutes"));
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
            
            System.out.println("\n=== Emotional Distribution ===");
            System.out.printf("%-15s %-10s %s%n", "Emotion", "Count", "Percentage");
            System.out.println("-----------------------------------");
            
            while (rs.next()) {
                System.out.printf("%-15s %-10d %.2f%%%n",
                    rs.getString("dominant_emotion"),
                    rs.getInt("emotion_count"),
                    rs.getDouble("percentage"));
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
            
            System.out.println("\n=== Platform Engagement Metrics ===");
            System.out.printf("%-15s %-10s %-10s %s%n", 
                "Platform", "Posts/Day", "Likes/Day", "Comments/Day");
            System.out.println("------------------------------------------------");
            
            while (rs.next()) {
                System.out.printf("%-15s %-10.2f %-10.2f %.2f%n",
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
            
            System.out.println("\n=== Top 5 Users by Engagement ===");
            System.out.printf("%-8s %-10s %-5s %-15s %-8s %s%n",
                "UserID", "Gender", "Age", "Platform", "Minutes", "Likes/Day");
            System.out.println("------------------------------------------------------------");
            
            while (rs.next()) {
                System.out.printf("%-8d %-10s %-5d %-15s %-8d %.2f%n",
                    rs.getInt("userID"),
                    rs.getString("gender"),
                    rs.getInt("age"),
                    rs.getString("platform_name"),
                    rs.getInt("Daily_Usage_Minutes"),
                    rs.getDouble("Likes_Received_Per_Day"));
            }
        }
    }
    
    public void testConnection() {
        try {
            Connection testConn = DatabaseConnection.getConnection();
            if (testConn != null) {
                System.out.println("Connection test successful!");
                testConn.close();
            }
        } catch (SQLException e) {
            System.err.println("Connection test failed: " + e.getMessage());
        }
    }
    
    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Database connection closed.");
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
                            analytics.showPlatformPopularity();
                            break;
                        case 2:
                            analytics.showAverageUsageTime();
                            break;
                        case 3:
                            analytics.showEmotionalDistribution();
                            break;
                        case 4:
                            analytics.showPlatformEngagement();
                            break;
                        case 5:
                            analytics.showTopUsers();
                            break;
                        case 6:
                            analytics.testConnection();
                            break;
                        case 7:
                            System.out.println("Exiting program...");
                            analytics.close();
                            return;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
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
