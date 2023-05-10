package sqlconnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;
import java.text.ParseException;

public class h2connection3 {
	Connection con = null;

	// table contact insert하기
	// table insert
	private void insertDeptprepared() throws ParseException {
		Scanner sc = new Scanner(System.in);
		int cid;
		String name;
		String category;
		String address;
		String company;
		
		if (true) {
			System.out.println("cid를 입력하세요 : " );
			cid = sc.nextInt();
			System.out.println("name을 입력하세요 : " );
			name = sc.next();
			System.out.println("category를 입력하세요 : "  );
			category = sc.next();
			System.out.println("address를 입력하세요 : "  );
			address = sc.next();
			System.out.println("company를 입력하세요 : "  );
			company = sc.next();
			System.out.println("birthday를 입력하세요 : "  );
			String birthday = sc.next();
			
			Date bir = Date.valueOf(birthday);
			

			try {
				PreparedStatement pr = con.prepareStatement(
						"insert into contact(cid, name ,category,address,company,birthday) values(?,?,?,?,?,?) ");

				pr.setInt(1, cid);
				pr.setString(2, name);
				pr.setString(3, category);
				pr.setString(4, address);
				pr.setString(5, company);
				pr.setDate(6, bir);
				pr.executeUpdate();
				System.out.println("데이터가 입력되었습니다.");
			} catch (Exception e) {
				e.printStackTrace();
				
			}
		}
		sc.close();
	}

	private boolean connectH2DB() {
		try {
			Class.forName("org.h2.Driver");
			con = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/telephone", "sa", "tiger");
			System.out.println("데이터베이스가 연결되었습니다.");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private void closeH2DB() {
		try {
			con.close();
			System.out.println("데이터베이스가 닫혔습니다.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws ParseException {

		h2connection3 tt = new h2connection3();

		if (tt.connectH2DB()) {
			tt.insertDeptprepared();
		}
		tt.closeH2DB();
	}
}
