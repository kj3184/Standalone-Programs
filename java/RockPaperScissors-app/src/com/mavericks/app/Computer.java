package com.mavericks.app;

import java.util.concurrent.ThreadLocalRandom;


public class Computer  implements Player {


	public int getPlayerInput() {
		int randomInt = ThreadLocalRandom.current().nextInt(1, 4);
		return randomInt;
	}
}
