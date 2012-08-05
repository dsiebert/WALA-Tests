package aa;

public class DirectUse {

	public native void runEcho();

	static {

		System.loadLibrary("echo");
	}

	public static void main(String[] args) {

//		new Echo().runEcho();
	}

}
