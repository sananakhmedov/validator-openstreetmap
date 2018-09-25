# OpenstreetMap Validator & Statistical Analyse

This project aims to validate openstreetMap places data by comparing with google places and other providers data.

## Getting Started

Exported osm map parts can be integrated into our application in order to validate places inside of it and make a
statistical analyse with them.

![capture](https://user-images.githubusercontent.com/43131798/45902807-5921fb00-bdf0-11e8-8020-fbb689a5bed9.PNG)
    
![capture](https://user-images.githubusercontent.com/43131798/45900963-f463a200-bde9-11e8-8b4e-fce3c9f48c73.PNG)


### Prerequisites

* npm - node package manager
    * download install npm [from here current latest version](https://www.npmjs.com/get-npm)
    * make sure it is configured as environment variable and accessible from cmd and restart all terminal sessions
* postgresql - database server
    * download and install postgresql [from here v.10 is okay](https://www.postgresql.org/)
    * database, user and postgis information should be configured, check installing part
    * while installing psql password can be chosen, in application default password is 'postgres' if you choose another one
    two places inside application should be altered. 
    
    > First one 
    ```shell
    $ in src/main/resources/application.properties
    $ spring.datasource.password = postgres
    ```
    > Second one 
    ```shell
    $ in src/main/resources/mybatis/config.xml
    $ <property name="password" value="postgres" />
    ```
* In order to run application osm file from [openstreetmap](https://www.openstreetmap.org/export#map=18/48.20816/16.37301)
should be exported, there are also some default map files inside;
    ```shell
    $ in src/main/resources/map
    ```

### Installing

> after postgresql server installed and in running state with following commands new database
> should be created.


> In case of psql command is not in environment variable.

Windows:

> For win its in program_files/postgresql/bin run psql command in the following path,
> If you want to run user postgres without password go into and change all md5 to trust
> at the bottom of the file. Than its possible to change password as empty.
> And also postgis bundle should be installed 
[from here](http://download.osgeo.org/postgis/windows/pg10/)
 
```shell
$ PostgreSQL\10\data\pg_hba.conf
```

> after changes saved, without password psql command works, open shell with admin privileges
```shell
$ <as Admin>
$ C:\Program Files\PostgreSQL\10\bin\psql -U postgres
```

OS X & Linux:

for mac

```shell
$ brew install postgis
```

For All
> and change password with following command inside psql terminal;
> after postgis is installed below extensions should be run inside of "map-db" database.
```shell
$ <as Admin>
$ ALTER USER postgres PASSWORD '';  //change password as empty
$ CREATE DATABASE 'map-db';  // create a database named map-db
$ \c map-db;   // and go inside to install extensions
$ CREATE EXTENSION postgis; CREATE EXTENSION hstore;
```

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

* user interface url path where application runs, open and start validation - [localhost:4210](http://localhost:4210)
* backend url path - [localhost:7090](https://localhost:7090)

## Built With

* [NodePackageManager](https://www.npmjs.com/) - Web framework package manager
* [Maven](https://maven.apache.org/) - Dependency Management
* [Angular](https://angular.io/) - User Interface Framework
* [osm2pgsql](https://github.com/openstreetmap/osm2pgsql) - Openstreetmap data to postgresql
* [postgis](https://www.postgresql.org/) - postgis map extension for pgsql 

## Authors

* **Sanan Ahmadzada** - *Initial work* - [GitHub](https://github.com/sananakhmedov)

## Acknowledgments

* Google has some restrictions for free requests, there are some daily request restrictions.
If you encounter such a problem, provide a new google and other third party providers places key and replace in the project.
* And microsoft api is right now available only for USA. They will announce the extension in further years.
* All key places are;
    * ```shell
      $ src/main/resources/config.properties
      $ pom.xml
      $ src/main/template/map-prj/src/index.html
      ```
* URL's for getting keys;
    * https://console.cloud.google.com/google/maps-apis
    * http://dev.virtualearth.net/REST/v1
    * https://developer.foursquare.com/  