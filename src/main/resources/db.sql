create database if not exists java_gobang;

use java_gobang;

drop table if exists user;
create table user(
    userId int primary key auto_increment,
    username varchar(50) unique,
    password varchar(50),
    score int,
    totalCount int,
    winCount int
);

insert into user values (null,'admin1','123',1000,0,0);
insert into user values (null,'admin2','123',1000,0,0);