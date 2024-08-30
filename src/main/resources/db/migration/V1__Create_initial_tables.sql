CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    roles VARCHAR(255)
);

CREATE TABLE hq (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    author VARCHAR(355),
    summary VARCHAR(800),
    year_of_publication YEAR,
    image VARCHAR(255),
    category VARCHAR(255),
    price DOUBLE,
    quantity INT
);

CREATE TABLE cart (
    id BIGINT AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE cart_items (
    cart_id BIGINT,
    product_id BIGINT,
    product_name VARCHAR(255),
    image VARCHAR(255),
    price DOUBLE,
    quantity INT,
    FOREIGN KEY (cart_id) REFERENCES cart(id)
);