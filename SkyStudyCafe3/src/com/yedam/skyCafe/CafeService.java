package com.yedam.skyCafe;

import java.util.List;
import java.util.Scanner;


import com.yedam.skyMember.Member;
import com.yedam.skyMember.MemberDAO;

public class CafeService {
	
	
	
	//공지사항
	public void getNotice() {
		CafeHost host = CafeDAO.getInstance().getNotice();
	
		
	}
	
	
	
	//운영진 메뉴
	//1.전체조회 2.회원조회(단건) 4.회원정보수정(이름수정,휴대폰번호수정) 5.회원삭제 6.종료"
	
	Scanner sc = new Scanner(System.in);
	public static CafeHost cafeHostInfo = null;

	
	public void login() {
		
	}
	
	
	
	//1.전체조회
	public void getMemberList() {
		List<Member> list = CafeDAO.getInstance().getMemberList();
			System.out.println("♣ 총 회원수: "+CafeDAO.getInstance().getAllMember().getCount()+"명");
			System.out.println("♣ 총 좌석수: 45석");
			System.out.println("♣ 남은 좌석: "+CafeDAO.getInstance().getEmptySeat().getCount_seat()+"석");
			System.out.println("============================<< 회 원 정 보 >>============================");
			System.out.println("회원번호 |  이름  | 좌석번호 |   가입날짜   |   퇴실날짜   |   금액   |   회원등급 ");
		for(Member member : list) {
			System.out.println("  "+member.getSky_no()+"\t "+member.getSky_name()
			+"\t  "+member.getSky_seat()+"\t  "+member.getStart_date()+"   "+member.getEnd_date()
			+"  "+member.getPay()+"원\t"+member.getSky_grade());
		}
			System.out.println("============================<< 회 원 정 보 >>============================");
			System.out.println();
	}	
	
	
	//2.회원조회(단건)
//			회원번호:
//				좌석번호:
//				이름 :
//				휴대폰 :
//				총 대여기간: 00/00~00/00

	public void getOneMember() {
		System.out.println("회원번호 입력>");
		int no = Integer.parseInt(sc.nextLine());
		Member mem = CafeDAO.getInstance().getOneMember(no);
		System.out.println("------------------------------");	
		System.out.println("------- "+mem.getSky_no()+"번 회원님의 정보 -------");
			System.out.println("●이   름: " + mem.getSky_name());
			System.out.println("●전화번호: " + mem.getSky_phone());
			System.out.println("●좌석번호: " + mem.getSky_seat());
			System.out.println("●대여기간: " + mem.getStart_date() +" ~ "+ mem.getEnd_date());
			System.out.println("●총대여일: " + mem.getDate_count()+"일");
			System.out.println("●회원등급: " + mem.getSky_grade());
			System.out.println("●금   액: " + mem.getPay()+"원");
			System.out.println("------------------------------");
			System.out.println("------------------------------");
	}

	

	
	
	//4.회원수정
	public void memUpdate() {
		Member mem = new Member();
		
		System.out.println("회원번호 > ");
		int memNo= Integer.parseInt(sc.nextLine());
		System.out.println("이름 수정 >");
		String memName= sc.nextLine();
		System.out.println("휴대폰번호 수정 > ");
		String memPhone = sc.nextLine();
		
		mem.setSky_no(memNo);
		mem.setSky_name(memName);
		mem.setSky_phone(memPhone);
				
		int result = CafeDAO.getInstance().memUpdate(mem);				
	}
		
	
	//5.회원삭제
	
	public void memDelete() {
		System.out.println("삭제할 회원 >");
		int memNo = Integer.parseInt(sc.nextLine());
		int result = CafeDAO.getInstance().memDelete(memNo);
		
		if(result>0) {
			System.out.println("회원이 삭제 되었습니다.");
			
		}else {
			System.out.println("회원삭제 실패했습니다.");
		}
	}
	
	
	
	
	//6.종료
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
