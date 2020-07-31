package Java_task;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.PreparedStatement;

public class Main {


	static final String URL = "jdbc:mysql://localhost/java_task?useSSL=false";
    static final String USERNAME = "root";
    static final String PASSWORD = "1234567890";

	//都道府県テーブルに必要な変数定義
	public static String kenCode;
	public static String kenNameKanji;
	public static String kenNameKana;

	//市町村区テーブルに必要な変数定義
	public static String shichoCode;
	public static String shichoNameKanji;
	public static String shichoNameKana;

	public static void main(String[] args) throws Exception {
		File file = new File("/Users/yoshitaka/ドキュメント/プログラミング/Java課題/市町村区コード/kadai01.csv");
		FileReader filereader = new FileReader(file);
		BufferedReader br = new BufferedReader(filereader);
		String str = br.readLine();
//		kenInsert ken = new kenInsert();
//		shichoInsert shicho = new shichoInsert();

		try {

			Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    		Statement statement = conn.createStatement();
    		while(str != null){
    			String[] arrs = str.split(",");
    			String code = arrs[0]; //配列の１つ目のコードだけ抽出

    			if(arrs.length == 4) { //県のデータか市町村のデータか判別

    				/*--------都道府県データのINSERT文の実行--------*/
    				kenCode = code.substring(0, 2); //codeの先頭2桁を県コードとして扱う
    				kenNameKanji = arrs[1];
    				kenNameKana = arrs[3];
//    				String kenArrs[] = {kenCode,kenNameKanji,kenNameKana};
//    				System.out.println(Arrays.toString(kenArrs));
    				//INSERT文の実行
    				String sqlInsert = "INSERT INTO prefecture(prefecture_id, prefecture_name_kanji, prefecture_name_kana)"
    						+ "VALUES ("+kenCode+",'"+kenNameKanji+"','"+kenNameKana+"');";
    				int result = statement.executeUpdate(sqlInsert);
//					System.out.println(kenCode + "/" + "OK" + result);
// 					ken.method();
    				/*------------------------------------------*/

    			} else {

    				/*--------市町村区データのINSERT文の実行--------*/
    				shichoCode = code.substring(2, 6); //codeの後ろ4桁を市町村コードとして扱う
    				shichoNameKanji = arrs[2];
    				shichoNameKana = arrs[4];
//    				String shichoArrs[] = {kenCode,shichoCode,shichoNameKanji,shichoNameKana};
//    				System.out.println(Arrays.toString(shichoArrs));
    				//INSERT文の実行
    				String sqlInsert = "INSERT INTO municipal_district(prefecture_id, municipal_district_code, municipal_district_name_kanji, municipal_district_name_kana)"
    	 				+ "VALUES ("+kenCode+" , "+shichoCode+" , '"+shichoNameKanji+"' , '"+shichoNameKana+"');";
    				int result = statement.executeUpdate(sqlInsert);
//					System.out.println(kenCode + "/" + shichoCode + "/" + "OK" + result);
// 					shicho.method();
    				/*------------------------------------------*/

    			}

    			//次のデータ読み込み
    			str = br.readLine();

    		}

    		/*--------都道府県テーブルからSELECT文で絞り込み、prefecture.csvとしてデータを出力する処理--------*/
    		//出力ファイル作成
    		FileWriter fileWriter = new FileWriter("/Users/yoshitaka/ドキュメント/プログラミング/Java課題/市町村区コード/prefecture.csv", false);
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(fileWriter));

            //SELECT文の発行
            String sqlSelect = "SELECT * FROM prefecture;";
            //実行するSQL文を指定する
    		PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sqlSelect);
            //SELECTを実行する
    		ResultSet rs = ps.executeQuery();

    		//取得した結果をファイルに書き込む
            while(rs.next()){
//                System.out.print(rs.getInt("prefecture_id") + "/" + rs.getString("prefecture_name_kanji") + "/" + rs.getString("prefecture_name_kana"));
//                System.out.println();
                printWriter.print(rs.getInt("prefecture_id"));
                printWriter.print(",");
                printWriter.print(rs.getString("prefecture_name_kanji"));
                printWriter.print(",");
                printWriter.print(rs.getString("prefecture_name_kana"));
                printWriter.println();
            }

            System.out.println("都道府県ファイル出力完了！");
            printWriter.close();
            /*---------------------------------------------------------------------------------------*/

            /*--------市町村区テーブルからSELECT文で絞り込み、municipal_district.csvとしてデータを出力する処理--------*/
            //出力ファイル作成
            fileWriter = new FileWriter("/Users/yoshitaka/ドキュメント/プログラミング/Java課題/市町村区コード/municipal_district.csv", false);
            printWriter = new PrintWriter(new BufferedWriter(fileWriter));

            //SELECT文の発行
            sqlSelect = "SELECT * FROM municipal_district;";
            //実行するSQL文を指定する
    		ps = (PreparedStatement) conn.prepareStatement(sqlSelect);
            //SELECTを実行する
    		rs = ps.executeQuery();

            //取得した結果を全件出力する
            while(rs.next()){
//                System.out.print(rs.getInt("prefecture_id") + "/" + rs.getInt("municipal_district_code") + "/" + rs.getString("municipal_district_name_kanji") + "/" + rs.getString("municipal_district_name_kana"));
//                System.out.println();
                printWriter.print(rs.getInt("prefecture_id"));
                printWriter.print(",");
                printWriter.print(rs.getInt("municipal_district_code"));
                printWriter.print(",");
                printWriter.print(rs.getString("municipal_district_name_kanji"));
                printWriter.print(",");
                printWriter.print(rs.getString("municipal_district_name_kana"));
                printWriter.println();
            }

            System.out.println("市町村区ファイル出力完了！");
            printWriter.close();
            /*---------------------------------------------------------------------------------------*/

		} catch (SQLException e) {
            e.printStackTrace();
        }

		br.close();
		filereader.close();
	}
}

//class kenInsert extends Main{
//	static final String URL = "jdbc:mysql://localhost/java_task?useSSL=false";
//    static final String USERNAME = "root";
//    static final String PASSWORD = "1234567890";
//	 void method(){
//		 try {
//	    		Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//	    		Statement statement = conn.createStatement();
//
////	    		//INSERT文の実行
//	    		String sqlInsert = "INSERT INTO prefecture(prefecture_id, prefecture_name_kanji, prefecture_name_kana)"
//	    				+ "VALUES ("+kenCode+",'"+kenNameKanji+"','"+kenNameKana+"');";
//
//	            int result = statement.executeUpdate(sqlInsert);
//	            System.out.println(kenCode + "OK" + result);
//
//	            //SELECTの実行
//	            String sqlSelect = "SELECT * FROM prefecture;";
//	            //実行するSQL文を指定する
//	    		PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sqlSelect);
//	            //SELECTを実行する
//	    		ResultSet rs = ps.executeQuery();
//	            //取得した結果を全件出力する
//	            while(rs.next()){
//	                System.out.print(rs.getInt("prefecture_id") + "/" + rs.getString("prefecture_name_kanji") + "/" + rs.getString("prefecture_name_kana"));
////	                System.out.print("/" + rs.getString("prefecture_name_kanji"));
////	                System.out.print("/" + rs.getString("prefecture_name_kana"));
//	                System.out.println();
//	            }
//
//	        } catch (SQLException e) {
//
//	            e.printStackTrace();
//
//	        }
//	 }
//}
//
//class shichoInsert extends Main{
//	static final String URL = "jdbc:mysql://localhost/java_task?useSSL=false";
//    static final String USERNAME = "root";
//    static final String PASSWORD = "1234567890";
//	 void method(){
//		 try {
//	    		Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//	    		Statement statement = conn.createStatement();
//
////	    		//INSERT文の実行
//	    		String sqlInsert = "INSERT INTO municipal_district(prefecture_id, municipal_district_code, municipal_district_name_kanji, municipal_district_name_kana)"
//	    				+ "VALUES ("+kenCode+" , "+shichoCode+" , '"+shichoNameKanji+"' , '"+shichoNameKana+"');";
//
//	            int result = statement.executeUpdate(sqlInsert);
//	            System.out.println(kenCode + shichoCode + "OK"  + result);
//
//	            //SELECT文の実行
//	            String sqlSelect = "SELECT * FROM municipal_district;";
//	            //実行するSQL文を指定する
//	    		PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sqlSelect);
//	            //SELECTを実行する
//	    		ResultSet rs = ps.executeQuery();
//
//	            //取得した結果を全件出力する
//	            while(rs.next()){
//	                System.out.print(rs.getInt("prefecture_id") + "/" + rs.getInt("municipal_district_code") + "/" + rs.getString("municipal_district_name_kanji") + "/" + rs.getString("municipal_district_name_kana"));
////	                System.out.print("/" + rs.getInt("municipal_district_code"));
////	                System.out.print("/" + rs.getString("municipal_district_name_kanji"));
////	                System.out.print("/" + rs.getString("municipal_district_name_kana"));
//	                System.out.println();
//	            }
//
//	        } catch (SQLException e) {
//
//	            e.printStackTrace();
//
//	        }
//	 }
//}
