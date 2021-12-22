Create table admin(
	id int,
	name varchar
);
insert into admin(id,name) values (1,'hirva');
Create table demo(
	id int PRIMARY KEY,
	name varchar,
	cno int
);
insert into demo(id,name,cno) values (1,'Parth',1234567890);
insert into demo(id,name,cno) values (2,'Hirva',1472583690);
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
insert into user(id,name) values (3,'sidharth');
insert into user(id,name) values (1,'hirva');
