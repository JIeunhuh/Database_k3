package sqlconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class mysql_Test02 {
	private void insertContactWithStatement(Connection con) {
		Statement st = null;
		PreparedStatement pr= null;
		String[] categories = { "friend", "coworker", "family", "etc" };
		Random rd = new Random();
		int totcnt = 10000;

		try {
			st = con.createStatement();

			for (int i = 1; i <= totcnt; i++) {
				String name = "name" + i;
				String cate = categories[rd.nextInt(4)];
				String addr = "addr" + i;
				String company = "company" + i;
				String birth = String.format("%4d-%02d-%02d", rd.nextInt(1950, 2023), rd.nextInt(1, 13),
						rd.nextInt(1, 29));

				String sql = String.format("insert into contact(cid, name, category,address,company,birthday)"
						+ " values(%d,'%s','%s','%s','%s','%s')", i, name, cate, addr, company, birth);
				// System.out.println(sql);//query문 출력
				st.executeUpdate(sql);

				System.out.println(i+"/1000000");
			
			
			}
//		try {
//			String sql = "insert into phone(seq,number,type) values(?,?,?)";
//			
//			pr = con.prepareStatement(sql);
//			while(true) {
//			pr.setInt(1, );
//			}
			System.out.println("입력종료");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { // 안정성이 높아짐

				if (st != null)
					st.close();
				if (con != null)
					con.close();
				System.out.println("시스템이 닫혔습니다.");
			} catch (SQLException w) {
				w.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		mysql_Test02 tt = new mysql_Test02();

		Connection con = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse", "scott", "tiger");
			System.out.println("db 연결");
			tt.insertContactWithStatement(con);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
