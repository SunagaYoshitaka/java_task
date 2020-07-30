package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.PreparedStatement;

public class ConnectionClass {

    static final String URL = "jdbc:mysql://localhost/java_task?useSSL=false";
    static final String USERNAME = "root";
    static final String PASSWORD = "1234567890";

    public static void main(String[] args) {

    	try {
    		Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    		Statement statement = conn.createStatement();

    		//INSERT文の実行
    		String sql = "INSERT INTO prefecture(prefecture_name_kanji, prefecture_name_kana)"
    				+ "VALUES ('テスト', 'ﾃｽﾄ');";
            int result = statement.executeUpdate(sql);
            System.out.println("結果１：" + result);

            //SELECTの実行
            sql = "SELECT * FROM prefecture;";
            //実行するSQL文を指定する
    		PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);

            //SELECTを実行する
    		ResultSet rs = ps.executeQuery();

            //取得した結果を全件出力する
            while(rs.next()){
                System.out.print(rs.getInt("prefecture_id"));
                System.out.print("/" + rs.getString("prefecture_name_kanji"));
                System.out.print("/" + rs.getString("prefecture_name_kana"));
                System.out.println();
            }

        } catch (SQLException e) {

            e.printStackTrace();

        }
    }
}