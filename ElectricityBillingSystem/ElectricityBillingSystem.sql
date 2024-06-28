create Database Electricity_Billing_System;
use Electricity_Billing_System;
drop table if exists Signup; 
create table Signup(meter_no varchar(20), employer_id varchar(10), username varchar(50), name varchar(20), password varchar(20), usertype varchar(20));
select * from Signup;

create table newCustomers(name varchar(30), meter_no varchar(20), address varchar(50), city varchar(30), state varchar(30), email varchar(50), phone_no varchar(10));
select * from newCustomers;