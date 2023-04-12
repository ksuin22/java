CREATE TABLE skyMember (
SKY_NO    NUMBER    primary key,
SKY_PW             VARCHAR2(20)  ,
SKY_NAME           VARCHAR2(12)  ,
SKY_PHONE          VARCHAR2(13) ,
SKY_SEAT           VARCHAR2(10) default '퇴실',
Start_date 	date default sysdate,
date_count  number default 0);

INSERT INTO skymember VALUES (99,99,'운영진','010-0000-1234',0,sysdate,0);

CREATE SEQUENCE sky_seq --시퀀스이름 EX_SEQ
INCREMENT BY 1 --증감숫자 1
START WITH 1 --시작숫자 1
MINVALUE 1 --최소값 1
MAXVALUE 100 --최대값 100
NOCYCLE --순환하지않음
NOCACHE; 

SELECT *from skymember;

INSERT INTO skymember (sky_no,sky_pw,sky_name,sky_phone,date_count)
VALUES (sky_seq.nextval,sky_seq.currval,'김열공','010-9999-8888',60);
INSERT INTO skymember (sky_no,sky_pw,sky_name,sky_phone,sky_seat,date_count)
VALUES (sky_seq.nextval,sky_seq.currval,'심심이','010-1118-5221','A1',10);
INSERT INTO skymember (sky_no,sky_pw,sky_name,sky_phone,sky_seat,date_count)
VALUES (sky_seq.nextval,sky_seq.currval,'김승리','010-4242-5555','A2',120);

select sky_no, sky_name, sky_phone,sky_seat, start_date, start_date + date_count as end_date, 
    trunc((start_date + date_count) - sysdate) as left_date
from skymember;


select sky_no, sky_name, sky_phone,sky_seat, start_date, start_date + date_count as end_date, 
        trunc((start_date + date_count) - sysdate) as left_date,
        date_count, CASE WHEN date_count < 90 THEN '일반' ELSE '골드' END AS sky_grade,
       CASE WHEN 
       date_count < 90 THEN date_count *1000 
       ELSE date_count *1000 * 0.9 END AS pay       
from skymember;

SELECT sky_no, sky_name, sky_phone, sky_seat, start_date,
  start_date + date_count AS end_date,
  TRUNC((start_date + date_count) - SYSDATE) AS left_date,
  date_count,
  CASE
    WHEN date_count < 30 THEN '일반'
    WHEN date_count >= 30 AND date_count < 60 THEN '열심'
    WHEN date_count >= 60 AND date_count < 90 THEN '최고'
    ELSE '엘리트'
  END AS sky_grade,
  CASE
    WHEN date_count < 30 THEN date_count * 10000
    WHEN date_count >= 30 AND date_count < 60 THEN date_count * 10000 * 0.95
    WHEN date_count >= 60 AND date_count < 90 THEN date_count * 10000 * 0.9
    ELSE date_count * 10000 * 0.85
  END AS pay
FROM skymember;
  
  
  

where sky_no=4;

UPDATE skymember
SET date_count = date_count+3
WHERE sky_no = 3;

commit;

SELECT COUNT(*) FROM skymember WHERE sky_seat = '퇴실';
SELECT COUNT(*)-1 FROM skymember;
SELECT *from skymember;

commit;

UPDATE skymember
SET sky_seat = 'A3'
WHERE sky_no = 3;

select *from skymember;

UPDATE skymember
SET sky_seat = 'A5'
WHERE sky_no = 4 AND NOT EXISTS (
    SELECT * FROM skymember
    WHERE sky_seat = 'A5'
);

desc skymember;

select * from skymember;

create table notice(
no number primary key,
post varchar(300));

insert into notice values (1,'SKY 독서실에 오신것을 환영합니다.');
select * from notice;

Create table seat(

seat_no varchar2(10) foreign Key,
seat_ox varchar2(10) );

CREATE TABLE seat (
  sky_no number,
  sky_seat varchar2(10),
  seat_ox VARCHAR2(10) default '□',
  FOREIGN KEY (sky_no) REFERENCES skymember(sky_no)
);
drop table seat;
select * from skymember;
select * from seat;
insert into seat values (2,'A6','□');
insert into seat values (3,'A6','■');


select * from notice;
insert into notice values (sky_seq.nextval,'SKY 독서실에 오신것을 환영합니다.');
insert into notice values (sky_seq.nextval,'SKY 독서실은 쾌적하고 조용하며 공부하기에 최적의 환경을 상시 제공하고 있으며 매일 코로나 방역을 실시하여 건강과 안전에 최우선을 기하고 있습니다.');
