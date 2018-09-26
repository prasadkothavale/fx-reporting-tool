package fxreport;

import org.junit.Test;

import controller.MainController;

public class MainTest {

	@Test
	public void test() throws Exception {
		String[] args = {"2018-09-06"};
		MainController.main(args);
	}
	
	@Test(expected = Exception.class)
	public void testException() throws Exception {
		String[] args = new String[0];
		MainController.main(args);
	}

}
