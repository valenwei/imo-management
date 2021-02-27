package jar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {

		System.out.println("d".compareToIgnoreCase("b"));

		SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");

		try {
			Date d = f.parse("20210225");
			Date d1 = f.parse("20210425");

			System.out.println(dateAddMonth(d, 2).compareTo(d1));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//        Connection cnn = null;
//        Statement stat = null;
//        ResultSet rs = null;
//        try {
//			// Class.forName("com.mysql.jdbc.Driver");
//        	Class.forName("com.mysql.cj.jdbc.Driver");
//			
//			String url = "jdbc:mysql://localhost:3306/tissing";
//			Properties prop = new Properties();
//			prop.setProperty("user", "root");
//			prop.setProperty("password","root000");
//			cnn = DriverManager.getConnection(url,prop);
//			stat = cnn.createStatement();
//			rs = stat.executeQuery("select * from t_permit");
//			while(rs.next()) {
//				System.out.println("permitId: " + rs.getString("permitId"));
//			}
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}finally {
//			if(null != rs) {
//				try {
//					rs.close();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			
//			if(null != stat) {
//				try {
//					stat.close();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			if(null != cnn) {
//				try {
//					cnn.close();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Date dateAddMonth(Date date, int month) throws Exception {

		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		rightNow.add(Calendar.MONTH, month);// 日期加3个月
		// rightNow.add(Calendar.DAY_OF_YEAR,10);//日期加10天
		return rightNow.getTime();
	}

}
