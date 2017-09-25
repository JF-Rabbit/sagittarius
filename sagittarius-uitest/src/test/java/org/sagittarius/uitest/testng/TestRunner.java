package org.sagittarius.uitest.testng;

public class TestRunner {
	
	public static void main(String[] args) {
		Runner runner = new Runner();
		runner.run(TestCase01.class);
	}

}
