package testone;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class SongDAO {
	private JdbcTemplate jdbcTemplate;
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	public SongDAO() {
		jdbcTemplate = JdbcTemplate.getInstance();
	}
	public void insertSong(SongVO svo) {
		String sql = "insert into \"SONG\" values (?, ?, 1)";
		try {
			conn = jdbcTemplate.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong (1, svo.getServeyCode());
			pstmt.setString(2, svo.getSongName()); 
			int result = pstmt.executeUpdate();
			System.out.println(result + "행이 삽입 되었습니다.");
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public boolean selectOne(String songname, long code){
		List<SongVO> ls = new ArrayList<>();
		boolean si=true;
		String sql = "select * from \"SONG\" where \"SERVEY_CODE\"= ? and \"SONGNAME\"=? ";
		try {
			conn = jdbcTemplate.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1,code);
			pstmt.setString(2, songname);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				si=false;
			}else if(!rs.next()) {
				si=true;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return si;
	}
	public void updateSongCount(String songname, long code) {
		String sql="update \"SONG\" set \"SONGCOUNT\"=(select \"SONGCOUNT\" from \"SONG\" where \"SERVEY_CODE\"="+code+"and"+"\"SONGNAME\"="+"'"+songname+"'"+""+")+1" +" where \"SERVEY_CODE\"="+code +"";
		try {
			conn = jdbcTemplate.getConnection();
			pstmt = conn.prepareStatement(sql);
			int result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
}
