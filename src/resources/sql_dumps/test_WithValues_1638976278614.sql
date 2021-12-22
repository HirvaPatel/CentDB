Create table admin(
	id int,
	name varchar
);
insert into admin(id,name) values (1,'hirva');
Create table order(
	orderid int PRIMARY KEY,
	ordernumber int,
	userid int FOREIGN KEY REFERENCES User(userid)
);
insert into order(orderid,ordernumber,userid) values (1,12,1);
Create table user(
	id int,
	name varchar
);
insert into user(id,name) values (3,'hirva');
insert into user(id,name) values (1,'hirva');
insert into user(id,name) values (2,'vikram');
