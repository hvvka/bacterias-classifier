CREATE TABLE Flagella (
  id     INTEGER PRIMARY KEY,
  alpha  TEXT NOT NULL,
  beta   TEXT NOT NULL,
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

INSERT INTO Flagella
(id, alpha, beta, number)
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
