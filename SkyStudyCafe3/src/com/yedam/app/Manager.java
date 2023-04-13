package com.yedam.app;

import java.util.Scanner;

import com.yedam.skyCafe.CafeService;
import com.yedam.skyMember.MemberService;

public class Manager {
	
	
	Scanner sc = new Scanner(System.in);
	CafeService cs = new CafeService();
	MemberService ms = new MemberService();
	
	int menu = 0;

	
	public Manager() {
		system();	
	}


	private void system() {
		while(true) {		
			
			//운영진일때(CafeService)
			//1.전체조회  2.회원개별조회  3.회원수정  4.회원삭제  5.종료
			if(MemberService.memberInfo.getSky_no()==999) {
				System.out.println("※※※※※※※※※※※※※※※※ 운영진 메뉴 ※※※※※※※※※※※※※※※※");
				System.out.println("1.전체조회  2.회원개별조회  3.회원수정(이름,폰번호)  4.회원삭제  5.종료");
				System.out.println("메뉴선택 : ");
				menu = Integer.parseInt(sc.nextLine());
				if(menu==1) { 
					cs.getMemberList();
				}else if(menu==2) {
					cs.getOneMember();
				}else if(menu==3) {
					cs.memUpdate();
				}else if(menu==4) {
					cs.memDelete();
				}else if(menu==5) {
					break;					
				}else {
					System.out.println("---메뉴를 다시 입력하세요.");
				}
				
			//회원일때(MemberService)
			//1.내 정보조회 2.대여연장 3.좌석변경 4.퇴실 5.종료
			}else{
				System.out.println("---------------- 회원 메뉴 ----------------");
				System.out.println("1.내 정보조회 2.기간연장 3.좌석변경 4.퇴실 5.종료");
				System.out.println("메뉴선택 : ");
				menu = Integer.parseInt(sc.nextLine());
				if(menu==1) {
					ms.getMember();
				}else if(menu==2) {
					ms.getDateAdd();
				}else if(menu==3) {
					ms.getSeatNumber();
				}else if(menu==4) {
					ms.getSeatOut();
				}else if(menu==5) {
					break;
				}else {
					System.out.println("---메뉴를 다시 입력하세요.");
				}
			}
			
			
			
			
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
//
}
