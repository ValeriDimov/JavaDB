DROP SCHEMA softuni_stores_system;
CREATE SCHEMA softuni_stores_system;
USE softuni_stores_system;

CREATE TABLE pictures(
`id` INT PRIMARY KEY AUTO_INCREMENT,
url VARCHAR(100) NOT NULL,
added_on DATETIME NOT NULL
);

CREATE TABLE categories(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(40) NOT NULL UNIQUE
);

CREATE TABLE products(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(40) NOT NULL UNIQUE,
best_before DATE,
price DECIMAL(10, 2) NOT NULL,
description TEXT,
category_id INT NOT NULL,
picture_id INT NOT NULL,
	CONSTRAINT fk_products_categories
    FOREIGN KEY (category_id)
    REFERENCES categories(id),
	CONSTRAINT fk_products_pictures
    FOREIGN KEY (picture_id)
    REFERENCES pictures(id)
);

CREATE TABLE towns(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE addresses(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(50) NOT NULL UNIQUE,
town_id INT NOT NULL,
	CONSTRAINT fk_addresses_towns
    FOREIGN KEY (town_id)
    REFERENCES towns(id)
);

CREATE TABLE stores(
id INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(20) NOT NULL UNIQUE,
rating FLOAT NOT NULL,
has_parking BOOLEAN DEFAULT FALSE,
address_id INT NOT NULL,
	CONSTRAINT fk_stores_addresses
    FOREIGN KEY (address_id)
    REFERENCES addresses(id)
);

CREATE TABLE employees(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`first_name` VARCHAR(15) NOT NULL,
`middle_name` CHAR,
`last_name` VARCHAR(20) NOT NULL,
salary DECIMAL(19, 2) NOT NULL DEFAULT 0,
hire_date DATE NOT NULL,
manager_id INT,
store_id INT NOT NULL,
	CONSTRAINT fk_employees_stores
    FOREIGN KEY (store_id)
    REFERENCES stores(id),
    CONSTRAINT fk_employees_employees
    FOREIGN KEY (manager_id)
    REFERENCES employees(id)
);

CREATE TABLE products_stores(
product_id INT NOT NULL,
store_id INT NOT NULL,
	CONSTRAINT pk_mapping_products_stores
    PRIMARY KEY (product_id, store_id),
    CONSTRAINT fk_mapping_products
    FOREIGN KEY (product_id)
    REFERENCES products(id),
    CONSTRAINT fk_mapping_stores
    FOREIGN KEY (store_id)
    REFERENCES stores(id)
);

-- -------------------------------------
INSERT INTO products_stores (product_id, store_id)
SELECT p.id, 1 FROM products_stores AS ps
	RIGHT JOIN products AS p ON p.id = ps.product_id
WHERE ps.store_id IS NULL;

-- -------------------------------------
UPDATE employees AS e
	JOIN stores AS s ON s.id = e.store_id
    SET e.manager_id = 3, e.salary = e.salary - 500
WHERE year(e.hire_date) > 2003 AND s.name NOT IN('Cardguard', 'Veribet');

-- -------------------------------------
DELETE FROM employees
WHERE salary >= 6000 AND id != manager_id;

-- -------------------------------------
SELECT first_name, middle_name, last_name, salary, hire_date FROM employees
ORDER BY hire_date DESC;

-- --------------------------------------
SELECT 
    pr.name AS product_name,
    pr.price,
    pr.best_before,
   concat(left(pr.description, 10), '...') AS short_description,
    pi.url
FROM
    products AS pr
        JOIN pictures AS pi ON pi.id = pr.picture_id
WHERE
    CHAR_LENGTH(pr.description) > 100
        AND YEAR(pi.added_on) < 2019
        AND pr.price > 20
ORDER BY pr.price DESC;
-- ----------------------------------------

SELECT s.name, count(ps.product_id) AS product_count, round(avg(p.price), 2) AS `avg` FROM stores AS s
	LEFT JOIN products_stores AS ps ON ps.store_id = s.id
	LEFT JOIN products AS p ON p.id = ps.product_id
GROUP BY s.id
ORDER BY product_count DESC, `avg` DESC, s.id;
-- ---------------------------------------

SELECT 
    CONCAT_WS(' ', e.first_name, e.last_name) AS Full_name,
    s.name AS Store_name,
    a.name AS Address,
    e.salary AS Salary
FROM
    employees AS e
        JOIN stores AS s ON s.id = e.store_id
        JOIN addresses AS a ON a.id = s.address_id
WHERE e.salary < 4000 
		AND a.name LIKE '%5%' 
        AND char_length(s.name) >= 8
        AND right(e.last_name, 1) LIKE 'n';
-- ----------------------------------------

SELECT 
	reverse(s.name) AS reversed_name, 
	concat_ws('-', upper(t.name), a.name) AS full_address,
    count(e.id) AS employees_count
FROM stores AS s
	JOIN addresses AS a ON a.id = s.address_id
    JOIN towns AS t ON t.id = a.town_id
    JOIN employees AS e ON e.store_id = s.id
GROUP BY s.id
ORDER BY full_address;
-- ----------------------------------------

DELIMITER $$
CREATE FUNCTION udf_top_paid_employee_by_store(store_name VARCHAR(50))
RETURNS TEXT
DETERMINISTIC
BEGIN
RETURN (SELECT 
concat(e.first_name, ' ', e.middle_name, '. ', e.last_name, ' works in store for ', year('2020-10-18') - year(e.hire_date) ,' years') -- {years of experience}
FROM stores AS s
	JOIN employees AS e ON e.store_id = s.id
WHERE s.name LIKE store_name
ORDER BY e.salary DESC
LIMIT 1);
END
$$
DELIMITER ;

SELECT udf_top_paid_employee_by_store('Keylex') as 'full_info';
DROP FUNCTION udf_top_paid_employee_by_store;
-- --------------------------------------------

DELIMITER $$
CREATE PROCEDURE udp_update_product_price (address_name VARCHAR (50))
BEGIN
	UPDATE products AS p
		JOIN products_stores AS ps ON p.id = ps.product_id
		JOIN stores AS s ON s.id = ps.store_id
		JOIN addresses AS a ON a.id = s.address_id
	SET p.price = 
		CASE
			WHEN (left(a.name, 1) = '0') THEN p.price + 100
			ELSE p.price + 200
	END
WHERE a.name LIKE address_name;
END
$$
DELIMITER ;
CALL udp_update_product_price('1 Cody Pass');
SELECT name, price FROM products WHERE id = 17;

