CREATE TABLE user_roles
(
    user_role_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(45) NOT NULL,
    role VARCHAR(45) NOT NULL
);
CREATE TABLE users
(
    userId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(45) NOT NULL,
    password VARCHAR(100) NOT NULL,
    enabled INT DEFAULT '1' NOT NULL
);
CREATE TABLE orders
(
    orderId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(25) NOT NULL,
    currLocationId INT NOT NULL,
    targLocationId INT,
    date DATETIME NOT NULL,
    status VARCHAR(30) DEFAULT 'PROCESSING' NOT NULL,
    phone VARCHAR(20) NOT NULL
);
CREATE TABLE avatars
(
    username VARCHAR(35) NOT NULL PRIMARY KEY,
    image MEDIUMBLOB
);
CREATE TABLE locations
(
    locationId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    lat DOUBLE NOT NULL,
    lng DOUBLE NOT NULL
);