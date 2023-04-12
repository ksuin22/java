package com.yedam.skyCafe;

import java.util.ArrayList;
import java.util.List;

import com.yedam.common.DAO;
import com.yedam.skyMember.Member;
import com.yedam.skyMember.MemberService;

public class CafeDAO extends DAO {
	
	private static CafeDAO cafeDao = new CafeDAO();
	
	public CafeDAO() {
	}
	
	
	public static CafeDAO getInstance() {
		if(cafeDao==null) {
			cafeDao = new CafeDAO();
		}		
		return cafeDao;
	}
	
	
	
	//공지사항
	public CafeHost getNotice() {
		CafeHost host = null;
		try {
			conn();
			String sql= "select * from notice";
			pstmt = conn.prepareStatement(sql);				
			rs= pstmt.executeQuery();
			
			if(rs.next()){
				host = new CafeHost();
				host.setNo(rs.getInt("no"));
				host.setPost(rs.getString("post"));
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			disconn();
		}
		return host;
	}
	
	
	
	
	
	//운영진 메뉴
	//1.전체조회 2.회원조회(단건) 4.회원정보수정(이름수정,휴대폰번호수정) 5.회원삭제 6.종료"
	
	
	//1.전체조회
		public List<Member> getMemberList() {
			List<Member> list = new ArrayList<>();
			Member member = null; 
			
			try {
				conn();
				String sql = "SELECT sky_no, sky_name, sky_phone, sky_seat, start_date,\r\n"
						+ "  start_date + date_count AS end_date,\r\n"
						+ "  TRUNC((start_date + date_count) - SYSDATE) AS left_date,\r\n"
						+ "  date_count,\r\n"
						+ "  CASE\r\n"
						+ "    WHEN date_count < 30 THEN '일반'\r\n"
						+ "    WHEN date_count >= 30 AND date_count < 60 THEN '열심'\r\n"
						+ "    WHEN date_count >= 60 AND date_count < 90 THEN '최고'\r\n"
						+ "    ELSE '엘리트'\r\n"
						+ "  END AS sky_grade,\r\n"
						+ "  CASE\r\n"
						+ "    WHEN date_count < 30 THEN date_count * 10000\r\n"
						+ "    WHEN date_count >= 30 AND date_count < 60 THEN date_count * 10000 * 0.95\r\n"
						+ "    WHEN date_count >= 60 AND date_count < 90 THEN date_count * 10000 * 0.9\r\n"
						+ "    ELSE date_count * 10000 * 0.85\r\n"
						+ "  END AS pay\r\n"
						+ "FROM skymember\r\n"
						+ "WHERE sky_no != 99";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					member = new Member();
					member.setSky_no(rs.getInt("sky_no"));
					member.setSky_name(rs.getString("sky_name"));
					member.setSky_seat(rs.getString("sky_seat"));
					member.setSky_phone(rs.getString("sky_phone"));
					member.setStart_date(rs.getDate("start_date"));
					member.setEnd_date(rs.getDate("end_date"));
					member.setLeft_date(rs.getInt("left_date"));
					member.setDate_count(rs.getInt("date_count"));
					member.setSky_grade(rs.getString("sky_grade"));
					member.setPay(rs.getInt("pay"));

					list.add(member);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				disconn();
			}
			
			return list;
		}
	
		
	//1-1.총 회원수
		public CafeHost getAllMember() {
			CafeHost host =null;

			try {
				conn();
				String sql= "SELECT COUNT(*)-1 FROM skymember";
				pstmt = conn.prepareStatement(sql);				
				rs = pstmt.executeQuery();
				
				if(rs.next()){
					host = new CafeHost();
					host.setCount(rs.getInt("count(*)-1"));

				}
				
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				disconn();
			}
			return host;

		}
		
	//1-2.남은 좌석수 
		public CafeHost getEmptySeat() {
			CafeHost host = null;
			try {
				conn();
				String sql= "SELECT 50-COUNT(*) as left_seat\r\n"
						+ "FROM skymember\r\n"
						+ "where sky_seat='퇴실'";
				pstmt = conn.prepareStatement(sql);				
				rs= pstmt.executeQuery();
				
				if(rs.next()){
					host = new CafeHost();
					host.setCount_seat(rs.getInt("left_seat"));

				}
				
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				disconn();
			}
			return host;
		}
		
	//2.회원 상세조회
//		회원번호: 
//		이   름: 
// 		전화번호:
//		좌석번호: 
//		대여기간: 00/00~00/00
//		남은기간: 5일
//		회원등급: 일반/골드등급 할인적용(첫 회원가입에 3개월 이상시 10%할인)
//		금   액: 00000원 (하루에 10000원)

	public Member getOneMember(int no) {
		Member mem =null;
		try {
			conn();
			String sql = "SELECT sky_no, sky_name, sky_phone, sky_seat, start_date,\r\n"
					+ "  start_date + date_count AS end_date,\r\n"
					+ "  TRUNC((start_date + date_count) - SYSDATE) AS left_date,\r\n"
					+ "  date_count,\r\n"
					+ "  CASE\r\n"
					+ "    WHEN date_count < 30 THEN '일반회원'\r\n"
					+ "    WHEN date_count >= 30 AND date_count < 60 THEN '열심회원'\r\n"
					+ "    WHEN date_count >= 60 AND date_count < 90 THEN '최고회원'\r\n"
					+ "    ELSE '엘리트회원'\r\n"
					+ "  END AS sky_grade,\r\n"
					+ "  CASE\r\n"
					+ "    WHEN date_count < 30 THEN date_count * 10000\r\n"
					+ "    WHEN date_count >= 30 AND date_count < 60 THEN date_count * 10000 * 0.95\r\n"
					+ "    WHEN date_count >= 60 AND date_count < 90 THEN date_count * 10000 * 0.9\r\n"
					+ "    ELSE date_count * 10000 * 0.85\r\n"
					+ "  END AS pay\r\n"
					+ "FROM skymember\r\n"
					+ "where sky_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			
			rs=pstmt.executeQuery();
			if(rs.next()) {
				mem=new Member();
				mem.setSky_no(rs.getInt("sky_no"));
				mem.setSky_name(rs.getString("sky_name"));
				mem.setSky_phone(rs.getString("sky_phone"));
				mem.setSky_seat(rs.getString("sky_seat"));
				mem.setStart_date(rs.getDate("start_date"));
				mem.setEnd_date(rs.getDate("end_date"));
				mem.setDate_count(rs.getInt("date_count"));
				mem.setLeft_date(rs.getInt("left_date"));
				mem.setSky_grade(rs.getString("sky_grade"));
				mem.setPay(rs.getInt("pay"));
				
			}else {
				System.out.println("회원번호가 존재하지 않습니다.");
			}
	
		} catch (Exception e) {
			e.printStackTrace(); 
		}finally {
			disconn();
		}
		return mem;
	}

		
		
		
	
	//수정(이름,폰번호)
	public int memUpdate(Member mem) {
		int result = 0;
		try {
			conn();
			String sql= "update skymember set sky_name=?, sky_phone = ? where sky_no = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mem.getSky_name());
			pstmt.setString(2, mem.getSky_phone());
			pstmt.setInt(3, mem.getSky_no());		
			
			result = pstmt.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			disconn();
		}
		return result;
	}
	
	
	
	
	//삭제
	public int memDelete(int memNo) {
		int result =0;
		try {
			conn();
			String sql = "delete from skymember where sky_no= ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memNo);
			
			result = pstmt.executeUpdate();
					
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();
			
		}

		return result;
	}
	
	
	
	
	

}
