CREATE TABLE Flagella (
  id INTEGER PRIMARY KEY,
  alfa TEXT NOT NULL,
  beta TEXT NOT NULL,
  number TEXT NOT NULL
);

CREATE TABLE Toughness (
  id INTEGER PRIMARY KEY,
  beta TEXT NOT NULL,
  gamma TEXT NOT NULL,
  rank TEXT NOT NULL
);

CREATE TABLE Examined (
   id INTEGER PRIMARY KEY,
   genotype TEXT NOT NULL,
   class TEXT NOT NULL
);

-- http://www.sqlitetutorial.net/sqlite-java/
-- http://www.sqlitetutorial.net/sqlite-java/create-database/
-- https://www.javatpoint.com/java-sqlite

