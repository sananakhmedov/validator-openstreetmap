# map
san-map
--prerequisites
* postgresql service should be active
* npm command should be defined as environment variable
npm start
* osm2plsql is already inside of project but for windows
 - http://download.osgeo.org/postgis/windows/pg10/ postgis should be installed
    and "CREATE EXTENSION postgis; CREATE EXTENSION hstore;" those extensions should be
    run inside of "map-db" database.

- if you are using linux machine or unix based system just run runscript.sh file
- command -> "sh runscript.sh"
- it will bootstrap backend first then ui in one file. 


first start spring application from OpenMapValidatorApplication.java
then start ui from path open-map-validator/src/main/template/map-prj with command "ng serve"
you need maybe npm install

- backend url path - localhost:8090
- ui path - localhost:4210
