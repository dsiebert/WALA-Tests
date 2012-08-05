package input;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadedCode {

	public void doSomething() {
		try {
			URL[]  classURLs=  new  URL[]{ new  URL("file:subdir/")};
			URLClassLoader  loader  =  new  URLClassLoader(classURLs);
			Class  loadedClass  =  Class.forName("loadMe",  true,  loader);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public void doSomething2() {
		try {
			URL[]  classURLs=  new  URL[]{ new  URL("file:subdir/")};
			ClassLoader  loader  =  new  URLClassLoader(classURLs);
		    Object  main  =  loader.loadClass("Main").newInstance();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void doSomething3() {
		try {
			URL[]  classURLs=  new  URL[]{ new  URL("file:subdir/")};
			URLClassLoader  loader  =  new  URLClassLoader(classURLs);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void doSomething4() {
		try {
			URL[]  classURLs=  new  URL[]{ new  URL("file:subdir/")};
			ClassLoader  loader  =  new  URLClassLoader(classURLs);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
