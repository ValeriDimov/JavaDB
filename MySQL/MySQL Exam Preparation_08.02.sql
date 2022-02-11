DROP SCHEMA stc;
CREATE SCHEMA stc;
use stc;

CREATE TABLE clients( 
id INT PRIMARY KEY AUTO_INCREMENT,
full_name VARCHAR(50) NOT NULL,
phone_number VARCHAR(20) NOT NULL
);

CREATE TABLE drivers( 
id INT PRIMARY KEY AUTO_INCREMENT,
first_name VARCHAR(30) NOT NULL,
last_name VARCHAR(30) NOT NULL,
age INT NOT NULL,
rating FLOAT DEFAULT 5.5
);

CREATE TABLE addresses( 
id INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(100) NOT NULL
);

CREATE TABLE categories( 
id INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(10) NOT NULL
);

CREATE TABLE cars( 
id INT PRIMARY KEY AUTO_INCREMENT,
make VARCHAR(20) NOT NULL,
model VARCHAR(20),
`year` INT NOT NULL DEFAULT 0,
`mileage` INT DEFAULT 0,
`condition` CHAR(1) NOT NULL,
`category_id` INT NOT NULL,
	CONSTRAINT fk_cars_categories
    FOREIGN KEY (category_id)
    REFERENCES categories(id)
);

CREATE TABLE cars_drivers( 
car_id INT NOT NULL,
driver_id INT NOT NULL,
	CONSTRAINT pk_mapping
    PRIMARY KEY (car_id, driver_id),
	CONSTRAINT fk_mapping_cars
    FOREIGN KEY (car_id)
    REFERENCES cars(id),
    CONSTRAINT fk_mapping_drivers
    FOREIGN KEY (driver_id)
    REFERENCES drivers(id)
);

CREATE TABLE courses( 
id INT PRIMARY KEY AUTO_INCREMENT,
from_address_id INT NOT NULL,
`start` DATETIME NOT NULL,
`bill` DECIMAL(10, 2) DEFAULT 10,
`car_id` INT NOT NULL,
`client_id` INT NOT NULL,
	CONSTRAINT fk_courses_addresses
    FOREIGN KEY (from_address_id)
    REFERENCES addresses(id),
    CONSTRAINT fk_courses_clients
    FOREIGN KEY (client_id)
    REFERENCES clients(id),
    CONSTRAINT fk_courses_cars
    FOREIGN KEY (car_id)
    REFERENCES cars(id)
);
-- ---------------------------------

INSERT INTO clients (full_name, phone_number)
SELECT concat(first_name, ' ', last_name), concat('(088) 9999', id * 2) FROM drivers
WHERE id BETWEEN 10 AND 20;

-- -----------------------------

UPDATE cars AS c
SET c.`condition` = 'C'
WHERE (c.mileage >= 800000 OR c.mileage IS NULL) AND c.`year` <= 2010 AND c.make != 'Mercedes-Benz';

-- -----------------------------

DELETE cl.* FROM clients AS cl
LEFT JOIN courses AS co ON cl.id = co.client_id
WHERE co.client_id IS NULL
AND char_length(full_name) > 3;
-- ------------------------------

SELECT make, model, `condition` FROM cars
ORDER BY id;
-- --------------------------------

SELECT d.first_name, d.last_name, c.make, c.model, c.mileage FROM drivers AS d
JOIN cars_drivers AS cd ON cd.driver_id = d.id
JOIN cars AS c ON c.id = cd.car_id
WHERE c.mileage IS NOT NULL
ORDER BY c.mileage DESC, d.first_name ASC;
-- -----------------------------------

SELECT c.id AS car_id, c.make, c.mileage, count(co.id) AS count_of_courses, round(avg(co.bill), 2) AS avg_bill FROM cars AS c
LEFT JOIN courses AS co ON c.id = co.car_id
GROUP BY c.id
HAVING count_of_courses != 2
ORDER BY count_of_courses DESC, c.id ASC;
-- ------------------------------------

SELECT c.full_name, count(co.car_id) AS count_of_cars, sum(co.bill) AS total_sum FROM clients AS c
JOIN courses AS co ON c.id = co.client_id
WHERE c.full_name LIKE '_a%'
GROUP BY c.full_name
HAVING count_of_cars > 1
ORDER BY c.full_name;
-- ------------------------------------

SELECT 
    a.name,
    CASE
        WHEN hour(co.`start`) BETWEEN 6 AND 20 THEN 'Day'
        ELSE 'Night'
    END AS `day_time`,
    co.bill,
    c.full_name,
    ca.make,
    ca.model,
    cat.name
FROM courses AS co
        LEFT JOIN addresses AS a ON co.from_address_id = a.id
        LEFT JOIN clients AS c ON c.id = co.client_id
        LEFT JOIN cars AS ca ON ca.id = co.car_id
        LEFT JOIN categories AS cat ON cat.id = ca.category_id
ORDER BY co.id;
-- ----------------------------------

DELIMITER $$
CREATE FUNCTION udf_courses_by_client(phone_num VARCHAR (20))
RETURNS INT
DETERMINISTIC
BEGIN
	RETURN (SELECT count(co.id) AS count FROM clients AS cl
JOIN courses AS co ON co.client_id = cl.id
WHERE cl.phone_number LIKE phone_num
GROUP BY cl.id);
END
$$
DELIMITER ;

SELECT udf_courses_by_client('(803) 6386812');
-- -------------------------------------

DELIMITER $$
CREATE PROCEDURE udp_courses_by_address (address_name VARCHAR (100))
BEGIN
	SELECT 
    a.name,
    cl.full_name,
    CASE
        WHEN co.bill BETWEEN 0 AND 20 THEN 'Low'
        WHEN co.bill <= 30 THEN 'Medium'
        ELSE 'High'
    END AS `level_of_bill`,
    ca.make,
    ca.condition,
    cat.name AS cat_name
FROM
    addresses AS a
        LEFT JOIN courses AS co ON co.from_address_id = a.id
        LEFT JOIN clients AS cl ON cl.id = co.client_id
        LEFT JOIN cars AS ca ON ca.id = co.car_id
        LEFT JOIN categories AS cat ON cat.id = ca.category_id
WHERE a.name LIKE address_name
ORDER BY ca.make ASC, cl.full_name;
END
$$
DELIMITER ;

CALL udp_courses_by_address('700 Monterey Avenue');
DROP PROCEDURE udp_courses_by_address;