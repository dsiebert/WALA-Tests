package errors.exposure;


public class Assert {

	public static void main(String[] args) {
		String email = "a@b.com";
		assert email != null;
		
	}

	private void doSomething (int something){
		assert something == 0;
	}
}
