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
		double memCount= Integer.parseInt(sc.nextLine());		
		
		Member member = new Member();
		member.setSky_name(memName);
		member.setSky_phone(memPhone);
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
		double count = Double.parseDouble(sc.nextLine());
		System.out.println("퇴실이 "+ count +"일 연장되었습니다.");
		System.out.println("추가 금액은 "+(count*10000)+"원 입니다.");
		double countplus = mem.getDate_count()+count;
		
		int result = MemberDAO.getInstance().getDateAdd(countplus);
	
	}
	
	
	
	//3.좌석변경 
	
	public void getSeatNumber() {
		String mySeat=MemberDAO.getInstance().getMember().getSky_seat();
		System.out.println("\n▶현재 회원님의 좌석: " +mySeat+"");
		
		char A = 65;
		List<Member> seatAry=MemberDAO.getInstance().getSeatPrint();
		
		System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
		System.out.println("  1 2 3 4 5 6 7 8 9");
		System.out.print(A+ " ");
		for(int i=1; i<seatAry.size(); i++) {
			System.out.print(seatAry.get(i).getSeat_ox()+ " ");
			if(i%9==0) {
				System.out.println();
				if(i<45) {
				A+=1;
				System.out.print(A + " ");
				}
			}
		}
		System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
		System.out.println("변경할 좌석을 선택하세요.");
		System.out.println("좌석 행 선택(A~E) > ");
		String row = sc.nextLine(); 
		System.out.println("좌석 열 선택(1~9) >");
		String col = sc.nextLine(); 
		System.out.println("선택하신 좌석은 "+ row+col +" 입니다.");
		
		String seatNum = MemberDAO.getInstance().getSeatNumber(row, col).getSky_seat();
		
		Member mem = new Member();
		mem.setSky_seat(seatNum);
		mem.setSky_no(memberInfo.getSky_no());
	
		MemberDAO.getInstance().getSeatChange(mem);
	}
	
	
	
	//4.퇴실하기
	
	public void getSeatOut() {
		saying();
		System.out.println("퇴실하시겠습니까?\n(퇴실:Y, 취소:N)>");
		String del = sc.nextLine();
		
		if(del.equals("Y")) {		
			Member mem=new Member();
			mem.setSeat_num(memberInfo.getSky_seat());
			mem.setSky_no(memberInfo.getSky_no());
			int result = MemberDAO.getInstance().getSeatOut(mem);

		}else if(del.equals("N")) {
			

			}
	}
	
	
	
	//명언 랜덤 출력
	public void saying() {

	String fighting[]= {
			"♧ 최선은 나를 절대 배반하지 않는다.",
			"♧ 오늘 걷지 않으면 내일은 뛰어야 한다.",
			"♧ 지금 흘린 침은 내일 흘릴 눈물이 된다.",
			"♧ 시간은 항상 나를 기다려주지 않는다",  
			"♧ 불가능은 노력하지 않는 자들의 변명이다.",
			"♧ 수학은 실수를 용납하지 않는다.",
			"♧ 질문에는 돈이 들지 않는다.",
			"♧ 불가능이란 노력하지 않는 자의 변명이다.",
			"♧ 행복은 성적순이 아니지만 성공은 성적순이다.",
			"♧ 아예 안 하는 것보다는 늦게 하는 것이 낫다.",
			"♧ 지금 잠을 자면 꿈을 꾸지만 지금 공부하면 꿈을 이룬다.",
			"♧ 내가 헛되어 보낸 오늘은 어제 죽은 이가 갈망하던 내일이다.",
			"♧ 늦었다고 생각했을 때... 그때가 가장 빠를 때이다.",
			"♧ 오늘 할 공부를 내일로 미루지 마라.  ",
			"♧ 눈이 감기는가? 그럼 미래를 향한 눈도 감긴다.",					
			"♧ 졸지 말고 자라.",
			"♧ 성적은 투자한 시간의 절대량에 비례한다.",
			"♧ 가장 위대한 일은 남들이 자고 있을 때 이뤄진다.",
			"♧ 지금 헛되이 보내는 이 시간이 시험을 코앞에 둔 시점에서 얼마나 절실하게 느껴지겠는가?"
			};
	
		int random = (int) Math.floor(Math.random()* fighting.length +1);
		System.out.println(fighting[random]);	
		System.out.println();
			
	}
	
	
	
	
	
//	String[][] seatAry= {
//			{"A","1","2","3","4","5","6","7","8","9"},
//			{"B","1","2","3","4","5","6","7","8","9"},
//			{"C","1","2","3","4","5","6","7","8","9"},
//			{"D","1","2","3","4","5","6","7","8","9"},
//			{"E","1","2","3","4","5","6","7","8","9"},	
//	};
//
//	
//	Member seat = MemberDAO.getInstance().getSeatNumber();
//	
//	String sss ="";
//	System.out.println("--- 좌 석 배 치 도 --- ");
//	for(int i=0; i<seatAry.length; i++) {
//		sss+="\n";
//		for(int j=0; j<seatAry[i].length; j++) {
//		sss+=seatAry[i][j]+" ";
//		}			
//	}		
//	System.out.println(sss);
//	System.out.println("--------------------");
//
//	if(row.equals(seatAry[0][0])) {	seatAry[0][col]="■";			
//	}else if(row.equals(seatAry[1][0])) {	seatAry[1][col]="■";
//	}else if(row.equals(seatAry[2][0])) {	seatAry[2][col]="■";
//	}else if(row.equals(seatAry[3][0])) {	seatAry[3][col]="■";
//	}else if(row.equals(seatAry[4][0])) {	seatAry[4][col]="■";
//	}else {			System.out.println("잘못 입력 하셨습니다. ");		}
//	
//
//	System.out.println("--- 좌 석 배 치 도 --- ");
//	
//	sss="";
//	for(int i=0; i<seatAry.length; i++) {
//		sss+="\n";
//		for(int j=0; j<seatAry[i].length; j++) {
//		sss+=seatAry[i][j]+" ";
//		}			
//	}
//	System.out.println(sss);
//	
//
//	int result = MemberDAO.getInstance().getSeatChange(seat);
	
	
	
	
	
}
