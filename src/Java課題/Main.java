package Java課題;

import java.io.FileReader;

public class Main {
	public static void main(String[] args) throws Exception {
		FileReader fr = new FileReader("/Users/yoshitaka/ドキュメント/プログラミング/Java課題/市町村区コード/kadai.csv");

		int input = fr.read();

		while (input != -1) {
			System.out.print((char)input);
			input = fr.read();
		}
		fr.close();
	}
}
