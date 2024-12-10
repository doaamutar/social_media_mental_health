-- Create the database
CREATE DATABASE IF NOT EXISTS social_media_mental_health;
USE social_media_mental_health;

-- Create User table
CREATE TABLE User (
    userID INT PRIMARY KEY,
    age INT,
    gender VARCHAR(45)
);

-- Create Platform table
CREATE TABLE Platform (
    platformID INT PRIMARY KEY AUTO_INCREMENT,
    platform_name VARCHAR(50) UNIQUE
);

-- Create Social Media Account table
CREATE TABLE Social_Media_Account (
    accountID INT PRIMARY KEY AUTO_INCREMENT,
    userID INT,
    platformID INT,
    FOREIGN KEY (userID) REFERENCES User(userID),
    FOREIGN KEY (platformID) REFERENCES Platform(platformID)
);

-- Create Interactions table
CREATE TABLE Interactions (
    interaction_id INT PRIMARY KEY AUTO_INCREMENT,
    account_id INT,
    Daily_Usage_Minutes INT,
    Posts_Per_Day INT,
    Likes_Received_Per_Day INT,
    Comments_Received_Per_Day INT,
    Messages_Sent_Per_Day INT,
    FOREIGN KEY (account_id) REFERENCES Social_Media_Account(accountID)
);

-- Create Emotional Status table
CREATE TABLE Emotional_Status (
    emotional_state_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    dominant_emotion VARCHAR(20),
    FOREIGN KEY (user_id) REFERENCES User(userID)
);

-- Create Daily Usage table
CREATE TABLE DailyUsage (
    usage_id INT PRIMARY KEY AUTO_INCREMENT,
    account_id INT,
    Minutes INT,
    usage_date DATE,
    FOREIGN KEY (account_id) REFERENCES Social_Media_Account(accountID)
);

-- Insert into Platform table
INSERT INTO Platform (platformID, platform_name) VALUES
(1, 'Snapchat'),
(2, 'Telegram'),
(3, 'Facebook'),
(4, 'Instagram'),
(5, 'LinkedIn'),
(6, 'Twitter'),
(7, 'Whatsapp');

-- Insert into User table
INSERT INTO User (userID, age, gender) VALUES
(500, 27, 'Female'),
(488, 21, 'Non-binary'),
(776, 28, 'Non-binary'),
(869, 27, 'Male'),
(573, 21, 'Non-binary'),
(428, 25, 'Female'),
(528, 25, 'Female'),
(773, 21, 'Non-binary'),
(382, 24, 'Non-binary'),
(505, 33, 'Male'),
(20, 32, 'Female'),
(444, 23, 'Non-binary'),
(720, 32, 'Female'),
(328, 25, 'Female'),
(372, 35, 'Male'),
(555, 31, 'Male'),
(181, 31, 'Male'),
(413, 34, 'Male'),
(132, 28, 'Male'),
(743, 29, 'Male'),
(526, 31, 'Male'),
(962, 23, 'Male'),
(497, 22, 'Female'),
(275, 25, 'Male'),
(836, 24, 'Non-binary'),
(35, 29, 'Male');

-- Insert into Social_Media_Account table
-- Using platformID instead of platform name
INSERT INTO Social_Media_Account (accountID, userID, platformID) VALUES
(1, 500, 1),  -- Snapchat
(2, 488, 1),  -- Snapchat
(3, 776, 1),  -- Snapchat
(4, 869, 2),  -- Telegram
(5, 573, 3),  -- Facebook
(6, 428, 4),  -- Instagram
(7, 528, 4),  -- Instagram
(8, 773, 3),  -- Facebook
(9, 382, 1),  -- Snapchat
(10, 505, 5), -- LinkedIn
(11, 20, 4),  -- Instagram
(12, 444, 3), -- Facebook
(13, 720, 4), -- Instagram
(14, 328, 4), -- Instagram
(15, 372, 6), -- Twitter
(16, 555, 5), -- LinkedIn
(17, 181, 2), -- Telegram
(18, 413, 5), -- LinkedIn
(19, 132, 4), -- Instagram
(20, 743, 6), -- Twitter
(21, 526, 3), -- Facebook
(22, 962, 7), -- Whatsapp
(23, 497, 3), -- Facebook
(24, 275, 2), -- Telegram
(25, 836, 5); -- LinkedIn

-- Insert into Interactions table
INSERT INTO Interactions (interaction_id, account_id, Daily_Usage_Minutes, Posts_Per_Day, 
                         Likes_Received_Per_Day, Comments_Received_Per_Day, Messages_Sent_Per_Day) VALUES
(1, 1, 120, 4, 40, 18, 22),
(2, 2, 60, 1, 18, 7, 12),
(3, 3, 115, 3, 38, 18, 27),
(4, 4, 105, 3, 48, 20, 28),
(5, 5, 55, 3, 17, 7, 12),
(6, 6, 160, 6, 85, 26, 30),
(7, 7, 160, 6, 85, 26, 30),
(8, 8, 55, 3, 17, 7, 12),
(9, 9, 85, 3, 33, 20, 18),
(10, 10, 45, 1, 10, 5, 12),
(11, 11, 140, 5, 70, 22, 33),
(12, 12, 105, 2, 25, 12, 18),
(13, 13, 140, 6, 75, 28, 30),
(14, 14, 160, 6, 85, 26, 30),
(15, 15, 70, 1, 13, 8, 10),
(16, 16, 60, 1, 15, 7, 17),
(17, 17, 50, 2, 20, 10, 12),
(18, 18, 65, 1, 14, 6, 15),
(19, 19, 145, 5, 75, 20, 35),
(20, 20, 95, 4, 50, 22, 22),
(21, 21, 80, 2, 20, 10, 20),
(22, 22, 70, 3, 22, 10, 18),
(23, 23, 70, 1, 14, 6, 10),
(24, 24, 80, 4, 30, 13, 25),
(25, 25, 55, 1, 11, 6, 11);

-- Insert into Emotional_Status table
INSERT INTO Emotional_Status (emotional_state_id, user_id, dominant_emotion) VALUES
(1, 500, 'Neutral'),
(2, 488, 'Neutral'),
(3, 776, 'Anxiety'),
(4, 869, 'Anxiety'),
(5, 573, 'Neutral'),
(6, 428, 'Happiness'),
(7, 528, 'Happiness'),
(8, 773, 'Neutral'),
(9, 382, 'Happiness'),
(10, 505, 'Boredom'),
(11, 20, 'Happiness'),
(12, 444, 'Neutral'),
(13, 720, 'Happiness'),
(14, 328, 'Happiness'),
(15, 372, 'Boredom'),
(16, 555, 'Anxiety'),
(17, 181, 'Sadness'),
(18, 413, 'Boredom'),
(19, 132, 'Happiness'),
(20, 743, 'Anger'),
(21, 526, 'Neutral'),
(22, 962, 'Anger'),
(23, 497, 'Neutral'),
(24, 275, 'Neutral'),
(25, 836, 'Boredom');

-- Insert into DailyUsage table
INSERT INTO DailyUsage (usage_id, account_id, Minutes, usage_date) VALUES
(1, 1, 120, '2024-12-06'),
(2, 2, 60, '2024-12-06'),
(3, 3, 115, '2024-12-06'),
(4, 4, 105, '2024-12-06'),
(5, 5, 55, '2024-12-06'),
(6, 6, 160, '2024-12-06'),
(7, 7, 160, '2024-12-06'),
(8, 8, 55, '2024-12-06'),
(9, 9, 85, '2024-12-06'),
(10, 10, 45, '2024-12-06'),
(11, 11, 140, '2024-12-06'),
(12, 12, 105, '2024-12-06'),
(13, 13, 140, '2024-12-06'),
(14, 14, 160, '2024-12-06'),
(15, 15, 70, '2024-12-06'),
(16, 16, 60, '2024-12-06'),
(17, 17, 50, '2024-12-06'),
(18, 18, 65, '2024-12-06'),
(19, 19, 145, '2024-12-06'),
(20, 20, 95, '2024-12-06'),
(21, 21, 80, '2024-12-06'),
(22, 22, 70, '2024-12-06'),
(23, 23, 70, '2024-12-06'),
(24, 24, 80, '2024-12-06'),
(25, 25, 55, '2024-12-06');

-- 1. Most popular social media platforms (by number of users)
SELECT 
    p.platform_name,
    COUNT(sma.accountID) as user_count
FROM Platform p
LEFT JOIN Social_Media_Account sma ON p.platformID = sma.platformID
GROUP BY p.platform_name
ORDER BY user_count DESC;

-- 2. Average usage time by platform
SELECT 
    p.platform_name,
    ROUND(AVG(i.Daily_Usage_Minutes), 2) as avg_daily_minutes
FROM Platform p
JOIN Social_Media_Account sma ON p.platformID = sma.platformID
JOIN Interactions i ON sma.accountID = i.account_id
GROUP BY p.platform_name
ORDER BY avg_daily_minutes DESC;

-- 3. Distribution of emotions across users
SELECT 
    dominant_emotion,
    COUNT(*) as emotion_count,
    ROUND((COUNT(*) * 100.0 / (SELECT COUNT(*) FROM Emotional_Status)), 2) as percentage
FROM Emotional_Status
GROUP BY dominant_emotion
ORDER BY emotion_count DESC;

-- 4. Average engagement metrics by platform
SELECT 
    p.platform_name,
    ROUND(AVG(i.Posts_Per_Day), 2) as avg_posts,
    ROUND(AVG(i.Likes_Received_Per_Day), 2) as avg_likes,
    ROUND(AVG(i.Comments_Received_Per_Day), 2) as avg_comments,
    ROUND(AVG(i.Messages_Sent_Per_Day), 2) as avg_messages
FROM Platform p
JOIN Social_Media_Account sma ON p.platformID = sma.platformID
JOIN Interactions i ON sma.accountID = i.account_id
GROUP BY p.platform_name
ORDER BY avg_likes DESC;

-- 5. Users with highest engagement (top 10)
SELECT 
    u.userID,
    u.gender,
    u.age,
    p.platform_name,
    i.Daily_Usage_Minutes,
    i.Posts_Per_Day,
    i.Likes_Received_Per_Day
FROM User u
JOIN Social_Media_Account sma ON u.userID = sma.userID
JOIN Platform p ON sma.platformID = p.platformID
JOIN Interactions i ON sma.accountID = i.account_id
ORDER BY i.Likes_Received_Per_Day DESC
LIMIT 10;

-- 6. Emotional distribution by gender
SELECT 
    u.gender,
    es.dominant_emotion,
    COUNT(*) as count
FROM User u
JOIN Emotional_Status es ON u.userID = es.user_id
GROUP BY u.gender, es.dominant_emotion
ORDER BY u.gender, count DESC;

-- 7. Average usage time by age group
SELECT 
    CASE 
        WHEN age < 25 THEN '18-24'
        WHEN age < 35 THEN '25-34'
        ELSE '35+'
    END as age_group,
    ROUND(AVG(i.Daily_Usage_Minutes), 2) as avg_daily_minutes,
    COUNT(DISTINCT u.userID) as user_count
FROM User u
JOIN Social_Media_Account sma ON u.userID = sma.userID
JOIN Interactions i ON sma.accountID = i.account_id
GROUP BY 
    CASE 
        WHEN age < 25 THEN '18-24'
        WHEN age < 35 THEN '25-34'
        ELSE '35+'
    END
ORDER BY age_group;

-- 8. Correlation between usage time and emotional state
SELECT 
    es.dominant_emotion,
    ROUND(AVG(i.Daily_Usage_Minutes), 2) as avg_daily_minutes,
    ROUND(AVG(i.Likes_Received_Per_Day), 2) as avg_likes,
    COUNT(DISTINCT u.userID) as user_count
FROM User u
JOIN Emotional_Status es ON u.userID = es.user_id
JOIN Social_Media_Account sma ON u.userID = sma.userID
JOIN Interactions i ON sma.accountID = i.account_id
GROUP BY es.dominant_emotion
ORDER BY avg_daily_minutes DESC;

-- 9. Platform preference by gender
SELECT 
    u.gender,
    p.platform_name,
    COUNT(*) as user_count
FROM User u
JOIN Social_Media_Account sma ON u.userID = sma.userID
JOIN Platform p ON sma.platformID = p.platformID
GROUP BY u.gender, p.platform_name
ORDER BY u.gender, user_count DESC;

-- 10. Users with multiple accounts
SELECT 
    u.userID,
    u.gender,
    u.age,
    COUNT(sma.accountID) as account_count,
    GROUP_CONCAT(p.platform_name) as platforms
FROM User u
JOIN Social_Media_Account sma ON u.userID = sma.userID
JOIN Platform p ON sma.platformID = p.platformID
GROUP BY u.userID, u.gender, u.age
HAVING COUNT(sma.accountID) > 1
ORDER BY account_count DESC;