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
	private String sky_seat; //skymember
	private Date start_date;
	private double date_count;
	
	private Date end_date;
	private int left_date;
	
	private String sky_grade;
	private int pay;
	
//
//		  sky_no number primary key,
//		  seat_abc varchar2(2),
//		  seat_num varchar2(1),
//		  seat_ox VARCHAR2(10) default '□'
	private String seatNumber; //seat테이블에 있는 좌석시리얼넘버
	private String seat_abc;
	private String seat_num; 
	private String seat_ox;
	

	
	
	
}