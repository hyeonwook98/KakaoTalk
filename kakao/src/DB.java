import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB{
	Connection conn;
	String url;
	String id;
	String pwd;
	String Driver = "com.mysql.cj.jdbc.Driver";
	
	public DB() {
		this.url = "jdbc:mysql://localhost:3306/madang?&serverTimezone=Asia/Seoul";
		this.id = "root";
		this.pwd = "root";		
	}
	
	public DB(String url, String id, String password) {		
		this.url = url;
		this.id = id;
		this.pwd = password;		
	}
	
	public Connection conn() {
		try {
			Class.forName(Driver); // MySQL 드라이버 로드
			conn = DriverManager.getConnection(url, id, pwd); // JDBC 연결
			System.out.println("DB 연결 완료");
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC 드라이버 로드 오류");
		} catch (SQLException e) {
			System.out.println("DB 연결 오류");
		}
		
		return conn;
	}

	public void connClose() throws SQLException {
		conn.close();
	}
	

}
