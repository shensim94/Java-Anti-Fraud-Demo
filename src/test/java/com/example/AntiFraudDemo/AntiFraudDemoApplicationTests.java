package com.example.AntiFraudDemo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class AntiFraudDemoApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void dummyTest() {
		assert 1 == 1;
	}

}
