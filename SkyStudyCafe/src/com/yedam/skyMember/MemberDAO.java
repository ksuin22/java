package com.yedam.skyMember;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.yedam.common.DAO;
import com.yedam.skyCafe.CafeHost;
import com.yedam.skyCafe.CafeService;


public class MemberDAO extends DAO{
	
	
	private static MemberDAO memberDao= new MemberDAO();
	
	private MemberDAO() {
		
	}
	
	
	public static MemberDAO getInstance() {
		return memberDao;
		
	}
	
	
	//1>로그인
	public Member login(int id) {
		Member member = null;
		try {
			conn();
			String sql = "SELECT *FROM skymember where sky_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				member = new Member();
				member.setSky_no(rs.getInt("sky_no"));
				member.setSky_pw(rs.getString("sky_pw"));
				member.setSky_name(rs.getString("sky_name"));
				member.setSky_phone(rs.getString("sky_phone"));
				member.setSky_seat(rs.getString("sky_seat"));

			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disconn();
		}
		return member;
		
	}
	
	//2>회원가입
	
		public int memberAdd(Member member) {
			int result=0;
			try {
				conn();
				String sql= "INSERT INTO skymember (sky_no,sky_pw,sky_name,sky_phone,sky_seat,start_date,date_count)\r\n"
						+ "VALUES (sky_seq.nextval,sky_seq.currval,?,?,?,?,?)";
				pstmt= conn.prepareStatement(sql);
				pstmt.setString(1, member.getSky_name());
				pstmt.setString(2, member.getSky_phone());
				pstmt.setString(3, member.getSky_seat());
				pstmt.setDate(4, (Date) member.getStart_date());
				pstmt.setInt(5, member.getDate_count());
				
				result = pstmt.executeUpdate();
				
				if(result ==1) {
					System.out.println("회원등록 완료");
//					String sql2= "SELECT sky_no FROM skymember WHERE sky_pw = ?";
//					pstmt = conn.prepareStatement(sql2);
//					pstmt.setInt(1, MemberService.memberInfo.getSky_no());
//					
//					rs=pstmt.executeQuery();
//					System.out.println("회원 번호는 "+member.getSky_no() +" 입니다.");
				}else {
					System.out.println("회원등록 실패");
				}
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				disconn();
			}
			return result;
		}
	
	
	
	
	
	//회원 로그인시
	//1.내 정보조회 2.기간연장 3.좌석변경 4.퇴실  5.종료
		

	

	//1.내정보 조회 (단건조회)
//		회원번호: 
//		이름: 
// 		전화번호:
//		좌석번호: 
//		전체 대여기간: 00/00~00/00
//		현재 남은기간: 5일
//		할인 적용유무: 할인 적용(첫 회원가입에 3개월 이상시 10%할인)
//		금액:  00000원 (하루에 10000원)

	public Member getMember() {
		Member mem =null;
		try {
			conn();
			String sql = "select sky_no, sky_name, sky_phone,sky_seat, start_date, date_count,start_date + date_count as end_date, \r\n"
					+ "    trunc((start_date + date_count) - sysdate) as left_date\r\n"
					+ "from skymember\r\n"
					+ "where sky_no= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, MemberService.memberInfo.getSky_no());
			
			rs=pstmt.executeQuery();
			if(rs.next()) {
				mem=new Member();
				mem.setSky_no(rs.getInt("sky_no"));
				mem.setSky_name(rs.getString("sky_name"));
				mem.setSky_phone(rs.getString("sky_phone"));
				mem.setSky_seat(rs.getString("sky_seat"));
				mem.setStart_date(rs.getDate("start_date"));
				mem.setEnd_date(rs.getDate("end_date"));
				mem.setLeft_date(rs.getInt("left_date"));
				mem.setDate_count(rs.getInt("date_count"));
			}
	
		} catch (Exception e) {
			e.printStackTrace(); 
		}finally {
			disconn();
		}
		return mem;
	}


	
	//2.기간연장
	public int getDateAdd(int count) {
		
		int result =0;
		try {
			conn();
			String sql = "UPDATE skymember\r\n"
					+ "SET date_count = date_count+? \r\n"
					+ "WHERE sky_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, count);
			pstmt.setInt(2, MemberService.memberInfo.getSky_no());
			
			result = pstmt.executeUpdate();
					
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();	
		}
		return result;
	}
	
	
	
	
	//3.좌석변경
	public int getSeat(Member member) {
		int result=0;
		try {
			conn();
			String sql = "UPDATE skymember\r\n"
					+ "SET sky_seat = ? \r\n"
					+ "WHERE sky_no = ? AND NOT EXISTS (\r\n"
					+ "    SELECT * FROM skymember\r\n"
					+ "    WHERE sky_seat = ? \r\n"
					+ ")";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getSky_seat());
			pstmt.setInt(2,MemberService.memberInfo.getSky_no());
			pstmt.setString(3, member.getSky_seat());
			
			result = pstmt.executeUpdate();
			
			if(result ==1) {
				System.out.println("좌석 변경 완료");
			}else {
				System.out.println("이미 사용되고 있는 좌석입니다.");
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();	
		}
		return result;
	}
	
	
	
	
	
	
	//4.퇴실
	public int memOut(int no) {
		
		int result =0;
		try {
			conn();
			String sql = "UPDATE skymember\r\n"
					+ "SET sky_seat = '퇴실'\r\n"
					+ "WHERE sky_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			
			result = pstmt.executeUpdate();
					
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();	
		}
		return result;
	}



	 

	

	
	
	
	
	
	
	

}//
