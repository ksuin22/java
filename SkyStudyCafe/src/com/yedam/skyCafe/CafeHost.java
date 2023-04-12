package com.yedam.skyCafe;

import java.sql.Date;

import lombok.Data;

@Data

public class CafeHost {
	
//	
//	SKY_NO     NOT NULL NUMBER(3) 
//	START_DATE          DATE      
//	END_DATE            DATE      
//	DATE_COUNT          NUMBER   
	
	private int sky_no;
	private Date start_date;
	private Date end_date;
	private int date_count;
	
	private int count;
	private int count_seat;
	
	private int no;
	private String post;
	

}
