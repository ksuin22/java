CREATE TABLE skyMember (
SKY_NO    NUMBER    primary key,
SKY_PW             VARCHAR2(20)  ,
SKY_NAME           VARCHAR2(12)  ,
SKY_PHONE          VARCHAR2(13) ,
SKY_SEAT           VARCHAR2(10) default '���',
Start_date 	date default sysdate,
date_count  number default 0);

INSERT INTO skymember VALUES (99,99,'���','010-0000-1234',0,sysdate,0);

CREATE SEQUENCE sky_seq --�������̸� EX_SEQ
INCREMENT BY 1 --�������� 1
START WITH 1 --���ۼ��� 1
MINVALUE 1 --�ּҰ� 1
MAXVALUE 100 --�ִ밪 100
NOCYCLE --��ȯ��������
NOCACHE; 

SELECT *from skymember;

INSERT INTO skymember (sky_no,sky_pw,sky_name,sky_phone,date_count)
VALUES (sky_seq.nextval,sky_seq.currval,'�迭��','010-9999-8888',60);
INSERT INTO skymember (sky_no,sky_pw,sky_name,sky_phone,sky_seat,date_count)
VALUES (sky_seq.nextval,sky_seq.currval,'�ɽ���','010-1118-5221','A1',10);
INSERT INTO skymember (sky_no,sky_pw,sky_name,sky_phone,sky_seat,date_count)
VALUES (sky_seq.nextval,sky_seq.currval,'��¸�','010-4242-5555','A2',120);

select sky_no, sky_name, sky_phone,sky_seat, start_date, start_date + date_count as end_date, 
    trunc((start_date + date_count) - sysdate) as left_date
from skymember;


select sky_no, sky_name, sky_phone,sky_seat, start_date, start_date + date_count as end_date, 
        trunc((start_date + date_count) - sysdate) as left_date,
        date_count, CASE WHEN date_count < 90 THEN '�Ϲ�' ELSE '���' END AS sky_grade,
       CASE WHEN 
       date_count < 90 THEN date_count *1000 
       ELSE date_count *1000 * 0.9 END AS pay       
from skymember;

SELECT sky_no, sky_name, sky_phone, sky_seat, start_date,
  start_date + date_count AS end_date,
  TRUNC((start_date + date_count) - SYSDATE) AS left_date,
  date_count,
  CASE
    WHEN date_count < 30 THEN '�Ϲ�'
    WHEN date_count >= 30 AND date_count < 60 THEN '����'
    WHEN date_count >= 60 AND date_count < 90 THEN '�ְ�'
    ELSE '����Ʈ'
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

SELECT COUNT(*) FROM skymember WHERE sky_seat = '���';
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

insert into notice values (1,'SKY �����ǿ� ���Ű��� ȯ���մϴ�.');
select * from notice;

Create table seat(

seat_no varchar2(10) foreign Key,
seat_ox varchar2(10) );

CREATE TABLE seat (
  sky_no number,
  sky_seat varchar2(10),
  seat_ox VARCHAR2(10) default '��',
  FOREIGN KEY (sky_no) REFERENCES skymember(sky_no)
);
drop table seat;
select * from skymember;
select * from seat;
insert into seat values (2,'A6','��');
insert into seat values (3,'A6','��');


select * from notice;
insert into notice values (sky_seq.nextval,'SKY �����ǿ� ���Ű��� ȯ���մϴ�.');
insert into notice values (sky_seq.nextval,'SKY �������� �����ϰ� �����ϸ� �����ϱ⿡ ������ ȯ���� ��� �����ϰ� ������ ���� �ڷγ� �濪�� �ǽ��Ͽ� �ǰ��� ������ �ֿ켱�� ���ϰ� �ֽ��ϴ�.');
