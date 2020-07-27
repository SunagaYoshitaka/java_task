package Java_task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

public class Main {
	public static void main(String[] args) throws Exception {
		File file = new File("/Users/yoshitaka/ドキュメント/プログラミング/Java課題/市町村区コード/kadai.csv");
		FileReader filereader = new FileReader(file);
		BufferedReader br = new BufferedReader(filereader);

		String str = br.readLine();
		while(str != null){
//			System.out.println(str);
			String[] arrs = str.split(",");
//			System.out.println(Arrays.toString(arrs)); //配列を文字列化して内容を表示
//			System.out.println(arrs);
//			for (String arr : arrs) {
//			    System.out.print(arr);
//			}
//			System.out.println();
			String code = arrs[0]; //配列の１つ目のコードだけ抽出
//			System.out.println(code);

			String kenCode = code.substring(0, 2); //codeの先頭2桁を県コードとして扱う
//			System.out.println(kenCode);

			String shichoCode = code.substring(2, 6); //codeの後ろ4桁を市町村コードとして扱う
//			System.out.println(shichoCode);

			if(arrs.length == 4) { //県のデータか市町村のデータか判別
				//県データの場合
//				System.out.println("ken");
				String kenArrs[] = {kenCode,arrs[1],arrs[3]};
				System.out.println(Arrays.toString(kenArrs));
			} else {
				//市町村データの場合
//				System.out.println("shicho");
				String shichoArrs[] = {kenCode,shichoCode,arrs[2],arrs[4]};
				System.out.println(Arrays.toString(shichoArrs));
			}

			str = br.readLine();

		}
		br.close();
		filereader.close();
	}
}
