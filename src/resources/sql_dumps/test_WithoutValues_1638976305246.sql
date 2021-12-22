Create table admin(
	id int,
	name varchar
);
Create table order(
	orderid int PRIMARY KEY,
	ordernumber int,
	userid int FOREIGN KEY REFERENCES User(userid)
);
Create table user(
	id int,
	name varchar
);
