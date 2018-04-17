# bacterias-classifier

## Run

If no local database is configured, then you may use:

1. **Sqlite** – the DB is in project and it is set to be a default one.

   Just select right URL and driver in `database.properties` or set it in runtime.
   
   New schema can be set with `cd db/sqlite && rm bacterias.db && sqlite3 bacterias.db < schema.sql` command

2. **MySQL** – run `./run-db.sh` script.

   It will download MySQL docker image with the server and establish 3307 port. Upload the schema from `db/mysql/schema.sql` to create the bacterias database. It can be done via [MySQL Workbench](https://dev.mysql.com/downloads/workbench/) (connected to the 3307 port, **not** default 3306).
   
For local MySQL database (running at local server) upload same schema as for the last point.

-------

## What app does?

For input bacteria's genotype it calculates its class based on information from database.  
[1-NN algorithm](https://en.wikipedia.org/wiki/K-nearest_neighbors_algorithm) is used for examination.

#### What is genotype?

A genotype is a string of six digits, e.g.: 

351244 

It encodes three genes:

34 – **alpha** (1st and 6th)

54 – **beta** (2nd and 5th)

12 – **gamma** (3rd and 4th)
 
#### What are the Flagella, Toughness and Examined tables?

Flagella and Toughness should be defined earlier (and they are in `schema.sql` scripts). They provide information on _number_ and _rank_ for the set of genes. 

Tables default content:

**Flagella**

| ALPHA | BETA | NUMBER |
|:-----:|:----:|:------:|
|   12  |  43  |    1   |
|   33  |  24  |    3   |
|   34  |  54  |    2   |
|   32  |  43  |    2   |

**Toughness**

| BETA | GAMMA | RANK |
|:----:|:-----:|:----:|
|  43  |   23  |   d  |
|  24  |   43  |   b  |
|  54  |   12  |   b  |
|  43  |   43  |   a  |

**Examined** is calculated and may look as the following one:

| GENOTYPE | CLASS |
|:--------:|:-----:|
|  123456  |   1b  |
|  234567  |   2a  |
|  142332  |   1d  |
|  324252  |   3b  |
