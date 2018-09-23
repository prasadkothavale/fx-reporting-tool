package fxreport;

import java.io.File;

import org.junit.Test;

import service.FileOperationsTest;

public class MainTest {

	@Test
	public void test() throws Exception {
		File file = new File(FileOperationsTest.class.getResource("/TestData.csv").toURI());
		String[] args = {file.getAbsolutePath()};
		Main.main(args);
		
		// negative test
		args = new String[0];
		Main.main(args);
	}

}
