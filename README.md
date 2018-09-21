# OpenstreetMap Validator & Statistical Analyse

This project aims to validate openstreetMap places data by comparing with google places and other providers data.

## Getting Started

Exported osm map parts can be integrated into our application in order to validate places inside of it and make a
statistical analyse with them.

![](https://user-images.githubusercontent.com/43131798/45900946-eb72d080-bde9-11e8-9336-dd5f56dae148.PNG)

![capture](https://user-images.githubusercontent.com/43131798/45902807-5921fb00-bdf0-11e8-8020-fbb689a5bed9.PNG)

### Prerequisites

* npm - node package manager
    * download install npm [from here current latest version](https://www.npmjs.com/get-npm)
    * make sure it is configured as environment variable and accessible from cmd and close all terminal sessions
* postgresql - database server
    * download and install postgresql [from here v.10 is okay](https://www.postgresql.org/)
    * database, user and postgis information should be configured, check installing part
    
### Installing

windows postgis bundle
http://download.osgeo.org/postgis/windows/pg10/
 
for mac
brew install postgis

psql -U postgres
CREATE DATABASE "map-database";

\c map-database;

CREATE EXTENSION postgis; CREATE EXTENSION hstore;


 - http://download.osgeo.org/postgis/windows/pg10/ postgis should be installed
    and "CREATE EXTENSION postgis; CREATE EXTENSION hstore;" those extensions should be
    run inside of "map-db" database.

- if you are using linux machine or unix based system just run runscript.sh file
- command -> "sh runscript.sh"
- it will bootstrap backend first then ui in one file. 

> after all third party apps installed like npm and postgresql below command can be run
> in ROOT

OS X & Linux:

```shell
$ runscript.sh
```

Windows:

```shell
$ runscript.bat
```
> These commands run backend and frontend applications in two different terminals and

* user interface url path where application runs - [localhost:4210](http://localhost:4210)
* backend url path - [localhost:7090](https://localhost:7090)

## Built With

* [NodePackageManager](https://www.npmjs.com/) - Web framework package manager
* [Maven](https://maven.apache.org/) - Dependency Management
* [Angular](https://angular.io/) - User Interface Framework

## Authors

* **Sanan Ahmadzada** - *Initial work* - [GitHub](https://github.com/sananakhmedov)

## Acknowledgments

* Google has some restrictions for free requests, there are some daily request restrictions.
If you encounter such a problem, provide a new google places key and replace in the project.
