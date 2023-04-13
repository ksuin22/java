
create table notice(
no number primary key,
post varchar(300));

insert into notice values (1,'SKY 독서실에 오신것을 환영합니다.');
select * from notice;

drop table seat;
CREATE TABLE seat (
  sky_no number primary key,
  seat_abc varchar2(2),
  seat_num varchar2(1),
  seat_ox VARCHAR2(10) default '□'
);

--seat > sky_no 시퀀스
create sequence seat_seq
INCREMENT BY 1 --증감숫자 1
START WITH 1 --시작숫자 1
MINVALUE 1 --최소값 1
MAXVALUE 100 --최대값 100
NOCYCLE --순환하지않음
NOCACHE; 
insert into seat values(0,'0','0','□');
select * from seat;

--seat 1~9
create sequence seat_seq9
INCREMENT BY 1 --증감숫자 1
START WITH 1 --시작숫자 1
MINVALUE 1 --최소값 1
MAXVALUE 9 --최대값 100
CYCLE --순환하지않음
NOCACHE; 

insert into seat values(0,'0','0','□');


CREATE TABLE skytest (
SKY_NO    NUMBER    primary key,
SKY_PW             VARCHAR2(20)  ,
SKY_NAME           VARCHAR2(12)  ,
SKY_PHONE          VARCHAR2(13) ,
SKY_SEAT   number   default 0     references seat(sky_no) ,
Start_date 	date default sysdate,
date_count  number default 0);



drop table seat;
select * from seat;
select * from skymember;

select sky_no, sky_seat
from skymember
where sky_no=2;

select * from seat;
insert into seat(sky_no,seat_abc,seat_num) values (seat_seq.nextval,'E',seat_seq9.nextval);
insert into seat(seat_abc,seat_num) values ('A',2);
insert into seat(seat_abc,seat_num) values ('A',3);
insert into seat(seat_abc,seat_num) values ('A',4);
insert into seat(seat_abc,seat_num) values ('A',5);
insert into seat(seat_abc,seat_num) values ('A',6);
insert into seat(seat_abc,seat_num) values ('A',7);
insert into seat(seat_abc,seat_num) values ('A',8);
insert into seat(seat_abc,seat_num) values ('A',9);

--좌석, 빈자리 넣는 
SELECT m.sky_no, m.sky_seat, s.seat_abc || s.seat_num
FROM seat s
JOIN skymember m ON s.sky_no = m.sky_no;


--no
select seat



select * from notice;
insert into notice values (sky_seq.nextval,'SKY 독서실에 오신것을 환영합니다.');
insert into notice values (sky_seq.nextval,'SKY 독서실은 쾌적하고 조용하며 공부하기에 최적의 환경을 상시 제공하고 있으며 매일 코로나 방역을 실시하여 건강과 안전에 최우선을 기하고 있습니다.');


select * from price;

create table price(
days number check days>0,
price number
sale number;

CREATE TABLE price (
  s_days NUMBER,
  s_price NUMBER default 10000,
  s_pay NUMBER GENERATED ALWAYS AS (days * price) VIRTUAL
  s_sale ;

drop table price;

select*from price;
DELETE FROM price WHERE days = 0.1;
insert into price(days, price) values (0.1, 10000);
insert into price(days, price) values (0.5, 10000);
insert into price(days, price) values (1, 10000);
insert into price(days, price) values (, 10000);

desc skymember;
desc skyprice;

    
CREATE TABLE skyprice (
  days NUMBER,
  price number 
  pay NUMBER GENERATED ALWAYS AS (days *) VIRTUAL,
  sale NUMBER GENERATED ALWAYS AS (
    CASE
      WHEN days >= 90 THEN pay * 0.15
      WHEN days >= 60 THEN pay * 0.1
      WHEN days >= 30 THEN pay * 0.05
      ELSE 0
    END
  ) VIRTUAL);
  
  CREATE TABLE skyprice (
  days NUMBER,
  price NUMBER(10) NOT NULL DEFAULT 10000,
  pay NUMBER GENERATED ALWAYS AS (days * price) VIRTUAL,
  sale NUMBER GENERATED ALWAYS AS (
    CASE
      WHEN days >= 90 THEN pay * 0.15
      WHEN days >= 60 THEN pay * 0.1
      WHEN days >= 30 THEN pay * 0.05
      ELSE 0
    END
  ) VIRTUAL
);

CREATE TABLE skyprice (
  sky_days NUMBER,
  sky_price NUMBER(10) DEFAULT 10000 NOT NULL CHECK (sky_price >= 0),
  sky_pay number,
  sky_sal NUMBER GENERATED ALWAYS AS (
    CASE
      WHEN sky_days >= 90 THEN sky_pay * 0.15
      WHEN sky_days >= 60 THEN sky_pay * 0.1
      WHEN sky_days >= 30 THEN sky_pay * 0.05
      ELSE 0
    END
  ) VIRTUAL
);

insert into skyprice(sky_days,sky_price) values (5,10000);

select*from skyprice;

update skypay
from skymember;

select*from seat;
select*From skyMember;

select sky_no
from seat
where seat_abc='A' and seat_num='3';


commit;

update skymember
set sky_seat=?
where sky_no=?;


UPDATE 테이블명
SET 열1=값1, 열2=값2, ...
WHERE 조건;

UPDATE employees
JOIN departments ON employees.department_id = departments.department_id
SET departments.department_name = 'IT'
WHERE employees.salary > 5000;


alter table seat
rename column sky_no to seatNumber;


select seat_ox from seat;
select *from skymember;
