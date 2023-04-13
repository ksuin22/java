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
				String sql= "INSERT INTO skymember (sky_no,sky_pw,sky_name,sky_phone,start_date,date_count)\r\n"
						+ "VALUES (sky_seq.nextval,sky_seq.currval,?,?,?,?)";
				pstmt= conn.prepareStatement(sql);
				pstmt.setString(1, member.getSky_name());
				pstmt.setString(2, member.getSky_phone());
				pstmt.setDate(3, (Date) member.getStart_date());
				pstmt.setDouble(4, member.getDate_count());
				
				result = pstmt.executeUpdate();
				
				if(result ==1) {
					System.out.println("회원등록 완료 : 내 정보조회를 통해 금액을 확인하세요. ");
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
			String sql = "\n"
					+ "SELECT sky_no, sky_name, sky_phone,seat_abc || seat_num as seatName,\n"
					+ "    start_date, date_count, start_date + date_count as end_date,\n"
					+ "    trunc((start_date + date_count) - sysdate) as left_date\n"
					+ "FROM skymember\n"
					+ "JOIN seat ON skymember.sky_seat = seat.seatnumber\n"
					+ "WHERE trunc((start_date + date_count) - sysdate) >= 0 \n"
					+ "and sky_no=? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, MemberService.memberInfo.getSky_no());
			
			rs=pstmt.executeQuery();
			if(rs.next()) {
				mem=new Member();
				mem.setSky_no(rs.getInt("sky_no"));
				mem.setSky_name(rs.getString("sky_name"));
				mem.setSky_phone(rs.getString("sky_phone"));
				mem.setSky_seat(rs.getString("seatName"));
				mem.setStart_date(rs.getDate("start_date"));
				mem.setEnd_date(rs.getDate("end_date"));
				mem.setLeft_date(rs.getInt("left_date"));
				mem.setDate_count(rs.getDouble("date_count"));
			}
	
		} catch (Exception e) {
			e.printStackTrace(); 
		}finally {
			disconn();
		}
		return mem;
	}


	
	//2.기간연장
	public int getDateAdd(double count) {
		
		int result =0;
		try {
			conn();
			String sql = "UPDATE skymember\r\n"
					+ "SET date_count = date_count+? \r\n"
					+ "WHERE sky_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setDouble(1, count);
			pstmt.setInt(2, MemberService.memberInfo.getSky_no());
			
			result = pstmt.executeUpdate();
					
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();	
		}
		return result;
	}
	
	
	
	//////좌석변경
	
	//3-1.좌석받아오기
	//입력한 값에서 받은 좌석 A1 > 쿼리문에서 skymember랑 같은 번호를 구한다. 
	public Member getSeatNumber(String a, String b) {
		
		Member mem =null;
		try {
			conn();
			String sql = "select seatNumber\n"
					+ "from seat\n"
					+ "where seat_abc=? and seat_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, a);
			pstmt.setString(2, b);
			
			rs=pstmt.executeQuery();
			if(rs.next()) {
				mem=new Member();
				mem.setSky_seat(rs.getString("seatNumber"));
	//			mem.setSky_seat(rs.getString("sky_seat"));
			}
	
		} catch (Exception e) {
			e.printStackTrace(); 
		}finally {
			disconn();
		}
		return mem;
	}

	
	
	//3.좌석변경
	public int getSeatChange(Member mem) {
		int result=0;
		try {
			conn();
			String sql = "update skymember\n"
					+ "set sky_seat=?\n"
					+ "where sky_no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,mem.getSky_seat());
			pstmt.setInt(2,mem.getSky_no());
			
			result = pstmt.executeUpdate();
			
			sql = "update seat set seat_ox='■' where seatNumber=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,mem.getSky_seat());
			
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
	
	
	
	//좌석출력

	public List<Member> getSeatPrint() {
		Member mem =null;
		List<Member> seatAry =new ArrayList<>();
		try {
			conn();
			String sql = "select seat_ox from seat";
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				mem=new Member();
				mem.setSeat_ox(rs.getString("seat_ox"));
				seatAry.add(mem);
			}
	
		} catch (Exception e) {
			e.printStackTrace(); 
		}finally {
			disconn();
		}
		return seatAry;
	}	
	
	
	
	//4.퇴실
	public int getSeatOut(Member mem) {
		int result=0;
		try {
			conn();
			String sql = "UPDATE seat \n"
					+ "SET seat_ox='□'\n"
					+ "WHERE seatNumber = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,mem.getSeatNumber());
			
			result = pstmt.executeUpdate();
			
			sql = "update skymember set skyseat=0 where sky_no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,mem.getSky_no());
			
			result = pstmt.executeUpdate();
			
			if(result ==1) {
				System.out.println("퇴실이 완료되었습니다.");
			}else {
				System.out.println("퇴실할 수 없습니다..");
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();	
		}
		return result;
	}
	
	
	
	public int memOut(int no) {
		
		int result =0;
		try {
			conn();
			String sql = "update skymember\n"
					+ "set sky_seat =0\n"
					+ "where sky_no=?";
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
