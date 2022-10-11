package com.github.onotoliy.ofiles.ofilesservice;

import org.junit.jupiter.api.Test;

class OFilesServiceApplicationTests {

	@Test
	void contextLoads() {
		System.out.println(DurationUtils.parse("P22DT32H42M"));
		System.out.println(DurationUtils.parse("PT32H42M"));
		System.out.println(DurationUtils.parse("PT42M"));
		System.out.println(DurationUtils.parse("PT42S"));
	}

}