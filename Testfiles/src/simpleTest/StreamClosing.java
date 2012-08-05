package simpleTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamClosing {

	public void doSomething(String path) {
		try {
			InputStream is = new FileInputStream(path);
			byte b[] = new byte[is.available()];
			is.read(b);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void doNotClose(String path) {
		try {
			InputStream is = new FileInputStream(path);
			byte b[] = new byte[is.available()];
			is.read(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void doSomethingFinal(String path) {
		InputStream is = null;
		try {
			is = new FileInputStream(path);
			byte b[] = new byte[is.available()];
			is.read(b);
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void doTwo(String path) {
		try {
			InputStream is = new FileInputStream(path);
			InputStream is2 = new FileInputStream(path);
			byte b[] = new byte[is.available()];
			is.read(b);
			is2.read(b);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void doOneFinal(String path) {
		InputStream is = null;
		InputStream is2 = null;
		try {
			is = new FileInputStream(path);
			is2 = new FileInputStream(path);
			byte b[] = new byte[is.available()];
			is.read(b);
			is2.read(b);
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
