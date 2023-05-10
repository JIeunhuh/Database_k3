package sqlconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class mysql2 {
	public static void main(String[] args) {

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse", "scott", "tiger");

			st = con.createStatement();
			rs = st.executeQuery("Select * from spj order by sno"); //.executeQuery()를 통해 쿼리문 입력함

			while (rs.next()) {
				System.out.println(String.format("%s, %s,%s, %d", 
						rs.getString("sno"), rs.getString("pno"),
						rs.getString("jno"), rs.getInt("qty")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { // 안정성이 높아짐
				if (rs != null)
					rs.close(); //역순으로 가야함
				if (st != null)
					st.close();
				if (con != null)
					con.close();
			} catch (SQLException w) {
				w.printStackTrace();
			}
		}
	}
}
