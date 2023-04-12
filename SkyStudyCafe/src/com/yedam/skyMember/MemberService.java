package com.yedam.skyMember;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import com.yedam.app.Exe;
import com.yedam.skyCafe.CafeDAO;


public class MemberService {

	public static Member memberInfo =null;
	Scanner sc = new Scanner(System.in);
	
	
	//1>로그인
	
	public void login() {
		Member member=new Member();
		System.out.println("회원번호 >");
		int id = Integer.parseInt(sc.nextLine());
		System.out.println("비밀번호 > ");
		String pw = sc.nextLine();
		
		member = MemberDAO.getInstance().login(id);
		
		if(member !=null) {	
			if(member.getSky_pw().equals(pw)){
				System.out.println("로그인 성공");
				memberInfo = member;			
			}else {
				System.out.println("패스워드 오류");
			}	
		}else {
			System.out.println("아이디 오류");
			Exe exe= new Exe();
			exe.run();			
		}		
	}
	
	
	
	//2>회원가입
	
	public void getMemberAdd() {
		
		System.out.println("이름 >");
		String memName= sc.nextLine();
		System.out.println("전화번호 >");
		String memPhone= sc.nextLine();
		System.out.println("시작할 날짜(입력양식:2023-02-01) >");
		//입력한 문자열 => java.sql.date로 변환해준다.
		Date start = Date.valueOf(sc.nextLine());
		
		
		System.out.println("대여할 일수 >");
		int memCount= Integer.parseInt(sc.nextLine());		
		System.out.println("좌석선택 >");
		String memSeat= sc.nextLine();
		
		Member member = new Member();
		member.setSky_name(memName);
		member.setSky_phone(memPhone);
		member.setSky_seat(memSeat);
		member.setStart_date(start);
		member.setDate_count(memCount);
		
		MemberDAO.getInstance().memberAdd(member);
	}	
	
	
	
	
	//회원 로그인시
	//1.내 정보조회 2.대여연장 3.좌석변경 4.퇴실 5.종료
	
	
	//1.내정보 조회
	public void getMember() {		

		Member mem = MemberDAO.getInstance().getMember();
		System.out.println("==========="+mem.getSky_name()+" 회원님의 정보===========");
			System.out.println("회원번호: " + mem.getSky_no());
			System.out.println("이   름: " + mem.getSky_name());
			System.out.println("전화번호: " + mem.getSky_phone());
			System.out.println("좌석번호: " + mem.getSky_seat());
			System.out.println("대여기간: " + mem.getStart_date() +" ~ "+ mem.getEnd_date());
			System.out.println("대여기간이 총 "+mem.getDate_count()+"일 중 "+(int)(mem.getLeft_date()+1)+"일 남았습니다.");
			System.out.println("======================================");
			System.out.println();	
	}
	
	
	//2.기간연장
	public void getDateAdd() {
		Member mem = new Member();
		System.out.println("연장할 일수를 입력하세요 >");
		int count = Integer.parseInt(sc.nextLine());
		System.out.println("퇴실이 "+ count +"일 연장되었습니다.");
		System.out.println("추가 금액은 "+(count*10000)+"원 입니다.");
		int countplus = mem.getDate_count()+count;
		
		int result = MemberDAO.getInstance().getDateAdd(countplus);
	
	}
	
	
	
	//3.좌석변경 
	
	public void getSeat() {
//		MemberSeat.getSeat();
		
		Member mem = new Member();
		System.out.println("변경할 좌석을 선택하세요. >");
		String seat = sc.nextLine();
		mem.setSky_seat(seat);
		int result = MemberDAO.getInstance().getSeat(mem);		
	
	}
	
	
	//4.퇴실하기
	
	public void memOut() {
		saying();
		System.out.println("퇴실하시겠습니까?(퇴실:1, 취소:0) >");
		int del = Integer.parseInt(sc.nextLine());
		if(del==0) {		
			}else if(del==1) {
				
			int result = MemberDAO.getInstance().memOut(memberInfo.getSky_no());
				if(result>0) {	
				System.out.println("퇴실처리 되었습니다.");				
				}else {
				System.out.println("퇴실이 취소 되었습니다.");
				}	
			}
	}
	
	
	
	//1.내정보 조회시 - 명언 랜덤 출력
	public void saying() {

	String fighting[]= {
			"▶ 최선은 나를 절대 배반하지 않는다.",
			"▶ 오늘 걷지 않으면 내일은 뛰어야 한다.",
			"▶ 지금 흘린 침은 내일 흘릴 눈물이 된다.",
			"▶ 시간은 항상 나를 기다려주지 않는다",  
			"▶ 불가능은 노력하지 않는 자들의 변명이다.",
			"▶ 수학은 실수를 용납하지 않는다.",
			"▶ 질문에는 돈이 들지 않는다.",
			"▶ 불가능이란 노력하지 않는 자의 변명이다.",
			"▶ 행복은 성적순이 아니지만 성공은 성적순이다.",
			"▶ 아예 안 하는 것보다는 늦게 하는 것이 낫다.",
			"▶ 지금 잠을 자면 꿈을 꾸지만 지금 공부하면 꿈을 이룬다.",
			"▶ 내가 헛되어 보낸 오늘은 어제 죽은 이가 갈망하던 내일이다.",
			"▶ 늦었다고 생각했을 때... 그때가 가장 빠를 때이다.",
			"▶ 오늘 할 공부를 내일로 미루지 마라.  ",
			"▶ 눈이 감기는가? 그럼 미래를 향한 눈도 감긴다.",					
			"▶ 졸지 말고 자라.",
			"▶ 성적은 투자한 시간의 절대량에 비례한다.",
			"▶ 가장 위대한 일은 남들이 자고 있을 때 이뤄진다.",
			"▶ 지금 헛되이 보내는 이 시간이 시험을 코앞에 둔 시점에서 얼마나 절실하게 느껴지겠는가?"
			};
	
		int random = (int) Math.floor(Math.random()* fighting.length +1);
		System.out.println(fighting[random]);		
			
	}
	
	
	
	//좌석배치도
//	
//	public void getSeatprint() {
//		
//		Scanner sc = new Scanner(System.in);
//
//		String A[] = {"A","1","2","3","4","5","6","7","8","9"};
//		String B[] = {"B","1","2","3","4","5","6","7","8","9"};
//		String C[] = {"C","1","2","3","4","5","6","7","8","9"};
//		String D[] = {"D","1","2","3","4","5","6","7","8","9"};
//		String E[] = {"E","1","2","3","4","5","6","7","8","9"};
//		
//		for(int i=0; i<A.length; i++) {System.out.print(A[i]+" ");}System.out.println();
//		for(int i=0; i<B.length; i++) {System.out.print(B[i]+" ");}System.out.println();
//		for(int i=0; i<C.length; i++) {System.out.print(C[i]+" ");}System.out.println();
//		for(int i=0; i<D.length; i++) {System.out.print(D[i]+" ");}System.out.println();
//		for(int i=0; i<E.length; i++) {System.out.print(E[i]+" ");}System.out.println();
//	
//		System.out.println("좌석 행 선택(A~E) > ");
//		String row = sc.nextLine(); 
//		System.out.println("좌석 열 선택(1~9) >");
//		int col = Integer.parseInt(sc.nextLine());
//	
//		String rc = row+col;
//		System.out.println("선택하신 좌석은 "+ rc +" 입니다.");
//	
//		if(row.equals(A[0])) {
//			A[col]="■";			
//		}else if(row.equals(B[0])) {
//			B[col]="■";
//		}else if(row.equals(C[0])) {
//			C[col]="■";		
//		}else if(row.equals(D[0])) {
//			D[col]="■";	
//		}else if(row.equals(E[0])) {
//			E[col]="■";				
//		}else {
//			System.out.println("잘못 입력 하셨습니다. ");
//		}
//		
//		for(int i=0; i<A.length; i++) {System.out.print(A[i]+" ");}System.out.println();
//		for(int i=0; i<B.length; i++) {System.out.print(B[i]+" ");}System.out.println();
//		for(int i=0; i<C.length; i++) {System.out.print(C[i]+" ");}System.out.println();
//		for(int i=0; i<D.length; i++) {System.out.print(D[i]+" ");}System.out.println();
//		for(int i=0; i<E.length; i++) {System.out.print(E[i]+" ");}System.out.println();
//	
//	}

	
	

	
	
	
}
