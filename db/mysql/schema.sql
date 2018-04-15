USE bacterias;

DROP TABLE IF EXISTS Flagella;
DROP TABLE IF EXISTS Toughness;
DROP TABLE IF EXISTS Examined;

DROP DATABASE bacterias;

CREATE DATABASE bacterias;
USE bacterias;

CREATE TABLE Flagella (
  id     int(20) NOT NULL AUTO_INCREMENT,
  alfa   char(2) NOT NULL,
  beta   char(2) NOT NULL,
  number char(1) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE Toughness (
  id    int(20) NOT NULL AUTO_INCREMENT,
  beta  char(2) NOT NULL,
  gamma char(2) NOT NULL,
  rank  char(1) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE Examined (
  id       int(20) NOT NULL AUTO_INCREMENT,
  genotype char(6) NOT NULL,
  class    char(2) NOT NULL,
  PRIMARY KEY (id)
);

INSERT INTO Flagella
(id, alfa, beta, number)
VALUES
  (null, '12', '43', '1'),
  (null, '33', '24', '3'),
  (null, '34', '54', '2'),
  (null, '32', '43', '2');

INSERT INTO Toughness
(id, beta, gamma, rank)
VALUES
  (null, '43', '23', 'd'),
  (null, '24', '43', 'b'),
  (null, '54', '12', 'b'),
  (null, '43', '43', 'a');

DELIMITER //
CREATE PROCEDURE get_examined
()
BEGIN
SELECT * FROM Examined;
END //
DELIMITER ;
