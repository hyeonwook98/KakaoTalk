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
			Class.forName(Driver); // MySQL ����̹� �ε�
			conn = DriverManager.getConnection(url, id, pwd); // JDBC ����
			System.out.println("DB ���� �Ϸ�");
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC ����̹� �ε� ����");
		} catch (SQLException e) {
			System.out.println("DB ���� ����");
		}
		
		return conn;
	}

	public void connClose() throws SQLException {
		conn.close();
	}
	

}
