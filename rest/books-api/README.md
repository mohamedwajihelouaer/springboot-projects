# Project Setup

## DB Setup

use the docker-compose file

```bash 
docker compose up -d
docker ps # to see the container we create is up or not
```

login as root into the server, use the defined password
```bash
docker exec -it mysql8-server-ctnr mysql -u root -p
# do admin work once logged in
```

```bash
docker exec -it mysql8-server-ctnr mysql -u root -prootpassword -e "CREATE DATABASE IF NOT EXISTS bookshop;"
```

## Flyway Migration

- add the dependency
- put the sql script under src/resources/db/migration
- name it V1__init-mysql-database.sql (for mysql database)
- go to properties file
- property to change, set to validate like this: spring.jpa.hibernate.ddl-auto=validate
- mvn flyway:clean -Dflyway.cleanDisabled=false
- mvn flyway:migrate



