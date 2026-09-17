
    drop table if exists author;

    drop table if exists book;

    create table author (
        birth_date date not null,
        id bigint not null auto_increment,
        first_name varchar(100) not null,
        last_name varchar(100) not null,
        address varchar(255) not null,
        email varchar(255) not null,
        phone varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table book (
        price_value decimal(10,2),
        publish_date date not null,
        id bigint not null auto_increment,
        title varchar(200) not null,
        isbn varchar(255),
        price_currency varchar(255),
        primary key (id)
    ) engine=InnoDB;

    alter table book 
       add constraint UKehpdfjpu1jm3hijhj4mm0hx9h unique (isbn);

    drop table if exists author;

    drop table if exists book;

    create table author (
        birth_date date not null,
        id bigint not null auto_increment,
        first_name varchar(100) not null,
        last_name varchar(100) not null,
        address varchar(255) not null,
        email varchar(255) not null,
        phone varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table book (
        price_value decimal(10,2),
        publish_date date not null,
        id bigint not null auto_increment,
        title varchar(200) not null,
        isbn varchar(255),
        price_currency varchar(255),
        primary key (id)
    ) engine=InnoDB;

    alter table book 
       add constraint UKehpdfjpu1jm3hijhj4mm0hx9h unique (isbn);

    drop table if exists author;

    drop table if exists book;

    create table author (
        birth_date date not null,
        id bigint not null auto_increment,
        first_name varchar(100) not null,
        last_name varchar(100) not null,
        address varchar(255) not null,
        email varchar(255) not null,
        phone varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table book (
        price_value decimal(10,2),
        publish_date date not null,
        id bigint not null auto_increment,
        title varchar(200) not null,
        isbn varchar(255),
        price_currency varchar(255),
        primary key (id)
    ) engine=InnoDB;

    alter table book 
       add constraint UKehpdfjpu1jm3hijhj4mm0hx9h unique (isbn);

    drop table if exists author;

    drop table if exists book;

    create table author (
        birth_date date not null,
        id bigint not null auto_increment,
        first_name varchar(100) not null,
        last_name varchar(100) not null,
        address varchar(255) not null,
        email varchar(255) not null,
        phone varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table book (
        price_value decimal(10,2),
        publish_date date not null,
        id bigint not null auto_increment,
        title varchar(200) not null,
        isbn varchar(255),
        price_currency varchar(255),
        primary key (id)
    ) engine=InnoDB;

    alter table book 
       add constraint UKehpdfjpu1jm3hijhj4mm0hx9h unique (isbn);
