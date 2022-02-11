DROP SCHEMA sgd;
CREATE SCHEMA sgd;
use sgd;

CREATE TABLE addresses(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(50) NOT NULL
);

CREATE TABLE offices(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`workspace_capacity` INT NOT NULL,
`website` VARCHAR(50),
`address_id` INT NOT NULL,
	CONSTRAINT fk_offices_addresses
    FOREIGN KEY (address_id)
    REFERENCES addresses(id)
);

CREATE TABLE employees(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`first_name` VARCHAR(30) NOT NULL,
`last_name` VARCHAR(30) NOT NULL,
`age` INT NOT NULL,
`salary` DECIMAL(10, 2) NOT NULL,
`job_title` VARCHAR(20) NOT NULL,
`happiness_level` CHAR NOT NULL
);

CREATE TABLE teams(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(40) NOT NULL,
`office_id` INT NOT NULL,
`leader_id` INT NOT NULL UNIQUE,
	CONSTRAINT fk_teams_offices
    FOREIGN KEY (office_id)
    REFERENCES offices(id),
    CONSTRAINT fk_teams_employees
    FOREIGN KEY (leader_id)
    REFERENCES employees(id)
);

CREATE TABLE games(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(50) NOT NULL UNIQUE,
`description` TEXT,
`rating` FLOAT NOT NULL DEFAULT 5.5,
`budget` DECIMAL(10, 2) NOT NULL,
`release_date` DATE,
`team_id` INT NOT NULL,
	CONSTRAINT fk_games_teams
    FOREIGN KEY (team_id)
    REFERENCES teams(id)
);


CREATE TABLE categories(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(10) NOT NULL
);

CREATE TABLE games_categories(
`game_id` INT NOT NULL,
`category_id` INT NOT NULL,
	CONSTRAINT pk_mapping
    PRIMARY KEY (game_id, category_id),
    CONSTRAINT fk_mapping_games
    FOREIGN KEY (game_id)
    REFERENCES games(id),
    CONSTRAINT fk_mapping_categories
    FOREIGN KEY (category_id)
    REFERENCES categories(id)
    );
    
    -- -----------------------------
    INSERT INTO games (`name`, rating, budget, team_id)
SELECT reverse(
substring((lower(t.name)), 2, char_length(t.name))) AS `name`, 
t.id AS rating, 
(t.leader_id * 1000) AS budget,
t.id AS team_id
FROM teams AS t
WHERE t.id BETWEEN 1 AND 9;

-- ------------------------------------
UPDATE employees AS e
JOIN teams AS t ON t.leader_id = e.id
SET e.salary = e.salary + 1000
WHERE e.id = t.leader_id AND e.age < 40 AND e.salary <= 5000;

-- -----------------------------------
DELETE g.* FROM games AS g
LEFT JOIN games_categories AS gc ON gc.game_id = g.id
WHERE gc.category_id IS NULL AND g.release_date IS NULL;

-- ------------------------------------
SELECT e.first_name, e.last_name, e.age, e.salary, e.happiness_level FROM employees AS e
ORDER BY e.salary ASC, e.id ASC;

-- ------------------------------------
SELECT t.name AS team_name, a.name AS address_name, char_length(a.name) AS count_of_characters FROM teams AS t
LEFT JOIN offices AS o ON o.id = t.office_id
LEFT JOIN addresses AS a ON a.id = o.address_id
WHERE o.website IS NOT NULL
ORDER BY t.name, a.name;

-- -------------------------------------
SELECT c.name, count(gc.game_id) AS games_count, round(avg(g.budget), 2) AS avg_budget, max(g.rating) AS max_rating FROM categories AS c
LEFT JOIN games_categories AS gc ON gc.category_id = c.id
LEFT JOIN games AS g ON g.id = gc.game_id
GROUP BY c.name
HAVING max_rating >= 9.5
ORDER BY games_count DESC, c.name ASC;

-- -------------------------------------
SELECT 
    g.`name`,
    g.release_date,
    CONCAT(LEFT(g.`description`, 10), '...') AS summary, 
    CASE 
		WHEN month(g.release_date) < 4 THEN 'Q1'
		WHEN month(g.release_date) < 7 THEN 'Q2'
		WHEN month(g.release_date) < 10 THEN 'Q3'
        ELSE 'Q4'
    END AS `quarter`,
    t.name AS team_name
FROM games AS g
JOIN teams AS t ON t.id = g.team_id 
WHERE
    YEAR(g.release_date) = 2022
        AND MONTH(g.release_date) % 2 = 0
        AND g.`name` LIKE '%2'
ORDER BY `quarter` ASC;
-- -------------------------------------

SELECT 
    g.name, 
    CASE
		WHEN g.budget < 50000 THEN 'Normal budget'
        ELSE 'Insufficient budget'
        END AS `age_group`,
    t.name AS team_name, 
    a.name AS address_name
FROM
    games AS g
        LEFT JOIN games_categories AS gc ON gc.game_id = g.id
        JOIN teams AS t ON t.id = g.team_id
        JOIN offices AS o ON o.id = t.office_id
        JOIN addresses AS a ON a.id = o.address_id
WHERE
    g.release_date IS NULL
        AND gc.category_id IS NULL
ORDER BY g.name ASC;
-- ------------------------------------
DROP FUNCTION udf_game_info_by_name;
DELIMITER $$
CREATE FUNCTION udf_game_info_by_name (game_name VARCHAR (20))
RETURNS TEXT
DETERMINISTIC
BEGIN
RETURN (SELECT 
concat_ws(' ','The', g.name, 'is developed by a', t.name, 'in an office with an address', a.name)
FROM
    games AS g
        JOIN teams AS t ON t.id = g.team_id
        JOIN offices AS o ON o.id = t.office_id
        JOIN addresses AS a ON a.id = o.address_id
WHERE
    g.name = game_name);
END 
$$
DELIMITER ;

SELECT udf_game_info_by_name('Fix San') AS info;
-- -----------------------------------------
DELIMITER $$
CREATE PROCEDURE `udp_update_budget`(min_game_rating FLOAT)
BEGIN
	UPDATE games AS g
LEFT JOIN games_categories AS gc ON gc.game_id = g.id
SET g.budget = g.budget + 100000, g.release_date =  ADDDATE(g.release_date, INTERVAL 1 Year)
WHERE gc.category_id IS NULL AND g.rating > min_game_rating AND g.release_date IS NOT NULL;
END
$$
DELIMITER ;
CALL udp_update_budget (8); 
drop PROCEDURE udp_update_budget;
