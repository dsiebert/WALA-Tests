package errors.exposure;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Exposure {

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			doSomething();
		} catch (IllegalArgumentException ase) {
			Logger.getGlobal().log(Level.INFO, "Caught:  " + ase.toString());
		}
	}

	public void doPost2(HttpServletRequest request, HttpServletResponse response) {
		try {
			doSomething();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	public void doPost3(HttpServletRequest request, HttpServletResponse response) {
		try {
			doSomething();
		} catch (IllegalArgumentException e) {
			System.out.print(e.toString());
		}
	}

	public void doPost4(HttpServletRequest request, HttpServletResponse response) {
		try {
			doSomething();
		} catch (IllegalArgumentException e) {
			System.err.print(e.toString());
		}
	}
	
	public void doPost5(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String something;
		} finally {
			System.err.print("");
		}
	}

	public void doSomething() throws IllegalArgumentException {
		System.err.print("");
	}
	
	
}
