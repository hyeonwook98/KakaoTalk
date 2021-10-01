import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DB_Connect {

	public static void main(String[] args) {
		Connection conn;
		PreparedStatement pstmt = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kakao?&serverTimezone=Asia/Seoul", "kakao", "root");
			System.out.println("DB 연결완료");
			
		}catch(ClassNotFoundException e) {
			System.out.println("JDBC 드라이버 로드 오류");
		}catch(SQLException e) {
			System.out.println("SQL 실행오류");
		}
	}	
}
	
