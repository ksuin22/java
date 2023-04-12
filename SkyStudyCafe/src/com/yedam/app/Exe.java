package com.yedam.app;

import java.util.Scanner;

import com.yedam.skyCafe.CafeService;
import com.yedam.skyMember.MemberService;

public class Exe {
	
	Scanner sc = new Scanner(System.in);
	int menu=0;
	MemberService ms= new MemberService();
	CafeService cs = new CafeService();

	public Exe() {
		System.out.println("***********************************");
		System.out.println("     SKY 독서실에 오신것을 환영합니다    ");
		System.out.println("***********************************");
		run();
	}
	
	public void run() {
		while(true) {			
			System.out.println("1.로그인  2.회원가입  3.공지사항  4.가격표  5.종료");
			System.out.println("메뉴선택 > ");
			menu = Integer.parseInt(sc.nextLine());
			
			if(menu==1) {
				ms.login();
				if(MemberService.memberInfo != null) {
					new Manager();
				}
			}else if(menu==2){
				System.out.println("회원가입을 환영합니다.");
				ms.getMemberAdd();
			}else if(menu==3){
				System.out.println("<SKY스터디 공지사항>");
				

			}else if(menu==4){
				System.out.println("  ▼▼▼▼▼ SKY스터디 가격 정보입니다 ▼▼▼▼▼\r");
				System.out.println("\t    1시간 1000원\r\n"
						+ "\t    5시간 5000원\r\n"
						+ "\t    1일 10000원\r\n"
						+ " 30일 이상 결제시  5% 할인 (열심회원 등극)\r\n"
						+ " 60일 이상 결제시 10% 할인 (최고회원 등극)\r\n"
						+ " 90일 이상 결제시 15% 할인 (엘리트회원 등극)\r\n");	
			}else if(menu==5){
				System.out.println("프로그램 종료");
				break;
			}else {
				System.out.println("잘못 입력하셨습니다.");
				
			}
		}
		
	}
	
	
	
	
	
}
