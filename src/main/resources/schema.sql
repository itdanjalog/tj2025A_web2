DROP DATABASE IF EXISTS springweb2;
CREATE DATABASE springweb2;
USE springweb2;

-- --------------------------------------- 실습1 ----------------------------------------
CREATE TABLE products (
    product_id INT PRIMARY KEY AUTO_INCREMENT,
    product_name VARCHAR(255) NOT NULL,
    stock_quantity INT NOT NULL
);

-- --------------------------------------- day06 example ----------------------------------------
CREATE TABLE student (
    sno INT AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    kor INT NOT NULL,
    math INT NOT NULL,
    CONSTRAINT PRIMARY KEY (sno)
);

-- --------------------------------------- day07 boardService13 ----------------------------------------
CREATE TABLE board(
    bno INT AUTO_INCREMENT,
    bcontent LONGTEXT NOT NULL,
    bwriter VARCHAR(30) NOT NULL,
    CONSTRAINT PRIMARY KEY (bno)
);

-- --------------------------------------- day09 trans ----------------------------------------
CREATE TABLE trans(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    money INT UNSIGNED DEFAULT 0
);

-- --------------------------------------- 실습3 ----------------------------------------
CREATE TABLE books (
    id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    PRIMARY KEY (id)
);

CREATE TABLE rentals (
    id INT NOT NULL AUTO_INCREMENT,
    book_id INT NOT NULL,
    member VARCHAR(100) NOT NULL,
    rent_date DATETIME DEFAULT NOW(),
    return_date DATETIME NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (book_id) REFERENCES books(id)
);
