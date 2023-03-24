package kaurami.me.t.springdemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

//@SpringBootTest
class SpringDemoApplicationTests {

	@Test
	void contextLoads() {
		String[] strings = {"str1", "str2", "str3"};
		testMethod(strings);
	}

	private void testMethod(String... strings){
		for (String s: strings) {
			System.out.println(s);
		}
	}

}
