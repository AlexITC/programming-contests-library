import java.io.*;
import java.util.*;
import java.awt.*;
import java.math.*;

public class Template implements Runnable {

	PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	StringTokenizer st = new StringTokenizer("");

	String next() {
		try {
			while (!st.hasMoreTokens()) {
				String s = br.readLine();
				if (s == null) {
					return null;
				}
				st = new StringTokenizer(s);
			}
			return st.nextToken();
		} catch (Exception e) {
			return null;
		}
	}

	static int read_int() {
		int res = 0, c;
		try {
			for (;;) {
				c = System.in.read() - 48;
				if (c < 0 || c >= 10)
					continue;
				res = c;
				break;
			}
			for (;;) {
				c = System.in.read() - 48;
				if (c < 0 || c >= 10)
					break;
				res = res * 10 + c;
			}
		} catch (IOException e) {
			System.err.println("err: read_int()");
		}
		return res;
	}

	public static void main(String[] args) {
		new Thread(null, new Template(), "Main", 1 << 28).start();
	}

	public void run() {
		//
		out.flush();
		System.exit(0);
	}
}


