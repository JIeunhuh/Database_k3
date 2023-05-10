package sqlconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class h2connection {
	public static void main(String[] args) {
		Connection con = null; //전역변수로 설정하기 !
		try {
			String driver = "org.h2.Driver";
			String url = "jdbc:h2:tcp://localhost/~/telephone"; // datamodel은 mysql에서 만든 database를 사용해야 함(db에 접근할 수 있는 경로)
			String username = "sa"; // username 입력
			String password = "tiger"; // pw 입력
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);

			System.out.println("Connection Success");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try { //try-catch문으로 또 싸야함
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}	
}