package kr.mem.model;
import java.sql.*;
//jdbc->mybatis
import java.util.ArrayList;
public class MemberDAO {
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	//동적로딩---실행할때 상태를 메모리에 올려주는것,,,이거먼저 실행해야한다.
	//초기화 블럭
	static {
		try {//DriverManager
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public Connection getConnect() {
		String url="jdbc:oracle:thin:@127.0.0.1:1521:XE";//protocol
		String user="hr";
		String password="hr";
		
		try {
			conn=DriverManager.getConnection(url,user,password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	public int memberInsert(MemberVO vo) {
		conn=getConnect();
		//mybatis
		String SQL="insert into tblMem values(seq_num.nextval,?,?,?,?,?)";
		int cnt=-1;
		try {
			ps=conn.prepareStatement(SQL);
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getPhone());
			ps.setString(3, vo.getAddr());
			ps.setDouble(4, vo.getLat());
			ps.setDouble(5, vo.getLng()); //여기까지 컴파일 시킨것
			cnt=ps.executeUpdate(); //날려주는 것 --insert문 실행후에 성공한 행의 수를 리턴하는 역할
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return cnt;
		
	}
	public ArrayList<MemberVO> memberAllList() {
		ArrayList<MemberVO> list =new ArrayList<MemberVO>();
		conn=getConnect();
		String SQL="select * from tblMem order by num desc";
		try {
			ps=conn.prepareStatement(SQL);
			rs=ps.executeQuery();//cursor 역할
			while(rs.next()) {
				int num=rs.getInt("num");
				String name=rs.getString("name");
				String phone=rs.getString("phone");
				String addr=rs.getString("addr");
				double lat=rs.getDouble("lat");
				double lng=rs.getDouble("lng");
				MemberVO vo=new MemberVO(num,name,phone, addr,lat,lng);
				list.add(vo);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return list;
	}
	   public int memberDelete(int num) {
		      conn=getConnect();
		      String SQL="delete from tblMem where num=?";
		      int cnt=-1;
		      try {
		       ps=conn.prepareStatement(SQL);
		       ps.setInt(1, num);
		       cnt=ps.executeUpdate(); // 1
		      } catch (Exception e) {
		      e.printStackTrace();
		    }finally {
				dbClose();
		    }
		      return cnt;
		   }
	   public void dbClose() {
		   try {
		   if(rs!=null) rs.close();
		   if(ps!=null) ps.close();
		   if(conn!=null)conn.close();
	   }catch(Exception e) {
		   e.printStackTrace();
	   }
	   }
	   public MemberVO memberContent(int num) {
		   MemberVO vo=null;
		   conn=getConnect();
		   String SQL="select * from tblMem where num=?";
		   try {
			ps=conn.prepareStatement(SQL);
			ps.setInt(1, num);
			rs=ps.executeQuery();
			if(rs.next()) {
				num=rs.getInt("num");
				String name=rs.getString("name");
				String phone=rs.getString("phone");
				String addr=rs.getString("addr");
				double lat=rs.getDouble("lat");
				double lng=rs.getDouble("lng");
				vo=new MemberVO(num,name,phone, addr,lat,lng);	
			}
		} catch (Exception e) { 
			e.printStackTrace();
		}finally {
			dbClose();
		}
		   return vo;
	   }
	   public int memberUpdate(MemberVO vo) {
		      conn=getConnect();
		      String SQL="update tblMem set phone=?,addr=? where num=?";
		      int cnt=-1;
		      try {
		      ps=conn.prepareStatement(SQL);
		      ps.setString(1, vo.getPhone());
		      ps.setString(2, vo.getAddr());
		      ps.setInt(3, vo.getNum());
		      cnt=ps.executeUpdate();
		   } catch (Exception e) {
		      e.printStackTrace();
		   }finally {
		      dbClose();
		   }
		     return cnt; 
		   }
}


