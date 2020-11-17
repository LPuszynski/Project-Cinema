create database ptest;
use ptest;

create table movies (title varchar(50), author varchar (50), runningTime int, genre varchar(20), releaseDate Date, ticketPrice decimal(2,2));
insert into movies values ("Harry Potter", "JK", 100, "fantastic", 12/11/2020,1.23);
insert into movies values ("test", "author du test", 90, "scary", 12/10/2020,16.30);
