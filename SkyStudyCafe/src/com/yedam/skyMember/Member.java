package com.yedam.skyMember;

import java.util.Date;

import lombok.Data;


@Data


public class Member {
	
	/*
CREATE TABLE skyMember (

SKY_NO    NUMBER    primary key,
SKY_PW             VARCHAR2(20)  ,
SKY_NAME           VARCHAR2(12)  ,
SKY_PHONE          VARCHAR2(13) ,
SKY_SEAT           VARCHAR2(10) default '퇴실',
Start_date 	date default sysdate,
date_count  number default 0);
	 * 
	 * 
	*/
	
	private int sky_no;
	private String sky_pw;
	private String sky_name;
	private String sky_phone;
	private String sky_seat;
	private Date start_date;
	private int date_count;
	
	private Date end_date;
	private int left_date;
	
	private String sky_grade;
	private int pay;
	
	
	
	
}