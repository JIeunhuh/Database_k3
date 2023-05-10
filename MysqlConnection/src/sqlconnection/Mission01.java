package sqlconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

class Query { // query class 생성
	int num;
	String text;
	String sql;

	// 생성자 만들기
	Query(int num, String text) {
		this.num = num;
		this.text = text;
	}

	Query(int num, String text, String sql) {
		this.num = num;
		this.text = text;
		this.sql = sql;
	}
	
	public int getNum() {
		return num;
	}

	public String getText() {
		return text;
	}
	
	public String getSql() {
		return sql;
	}
}

public class Mission01 {
	// connection객체 변수 설정
	static Connection con = null;

	void selectQuery1() {

		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select jname from j where city in ('london')");
			ResultSetMetaData rsmd = rs.getMetaData();

			System.out.println(rsmd.getColumnName(1));
			while (rs.next()) {
				System.out.println(String.format("'%s'", rs.getString("jname")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void selectQuery2() {

		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select sname from spj, s where spj.sno=s.sno and spj.jno='j1'");
			ResultSetMetaData rsmd = rs.getMetaData();
			int col = rsmd.getColumnCount();

			System.out.print(rsmd.getColumnName(1) + " ");
			System.out.println("\n" + "----------");
			while (rs.next()) {
				System.out.println(String.format("%s", rs.getString("sname")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void selectQuery3() {

		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(
					"select spj.sno, spj.pno, spj.qty from spj where spj.qty>300 and spj.qty<=750 order by qty asc");
			ResultSetMetaData rsmd = rs.getMetaData();
			int col = rsmd.getColumnCount();
			for (int i = 1; i <= col; i++) {
				System.out.print(rsmd.getColumnName(i) + " ");
			}
			System.out.println("\n" + "---------------------");
			while (rs.next()) {
				System.out.println(String.format("%s, %s, %s", rs.getString("spj.sno"), rs.getString("spj.pno"),
						rs.getString("spj.qty")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void selectQuery4() {

		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select distinct color, city from p");
			ResultSetMetaData rsmd = rs.getMetaData();
			int col = rsmd.getColumnCount();
			for (int i = 1; i <= col; i++) {
				System.out.print(rsmd.getColumnName(i) + " ");
			}
			System.out.println("\n" + "---------------------");
			while (rs.next()) {
				System.out.println(String.format("%s, %s", rs.getString("color"), rs.getString("city")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// connectDB()
	private boolean connectDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse", "scott", "tiger");
			System.out.println("데이터베이스가 연결되었습니다.");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// closeDB()
	void closeDB() {
		try {
			con.close();
			System.out.println("데이터베이스가 닫혔습니다.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ArrayList<Query> list = new ArrayList<>(); // arraylist<> 제너릭 만들어서 query class호출

		list.add(new Query(1, "London에 있는 프로젝트 이름을 찾아라")); // .add()써서 배열 만들기
		list.add(new Query(2, "j1에 참여하는 공급자의 이름"));
		list.add(new Query(3, "공급수량 300이상 750이하인 모든 공급의 sno,pno,qty"));
		list.add(new Query(4, "부품의 color와 city의 모든쌍 찾기 같은 쌍은 한번만 검색"));
//		list.add(new Query(5, "같은 도시에 있는 s,p,j의 모든 sno,pno,jno 쌍 찾기 찾아진결과의 도시가 모두 같아야 함"));

		Scanner sc = new Scanner(System.in);

		Mission01 query = new Mission01();

		if (query.connectDB()) {
			while (true) {
				for (Query qu : list) { // 확장 for문 이용해서 배열 하나씩 가져오기
					System.out.println(String.format("%d,'%s'", qu.getNum(), qu.getText()));
				}
				System.out.println("선택 <0:exit> : ");
				int sel = sc.nextInt();
				// 조건문으로 문제에 해당하는 쿼리문 가져오게 하기
//				if (sel == 0)
//					break;
//				else if (sel == 1) {
//					query.selectQuery1();
//				} else if (sel == 2) {
//					query.selectQuery2();
//				} else if (sel == 3) {
//					query.selectQuery3();
//				} else if (sel == 4) {
//					query.selectQuery4();
//				}
				switch (sel) {
				case 1:
					query.selectQuery1();
					break;
				case 2:
					query.selectQuery2();
					break;
				case 3:
					query.selectQuery3();
					break;
				case 4:
					query.selectQuery4();
					break;

				default:
					query.closeDB();
					return;
				}
				// 조건문 써서 문제 입력하면 해당쿼리문이 출력할 수 있도록 함
			}
		}
		query.closeDB();
		sc.close();
	}
}
