package com.mavericks.app;

import java.util.Scanner;

public class Human implements Player{
	private String userName;
	Scanner sc = new Scanner(System.in);

	public String getUserName() {
		return userName;
	}

	public void setUserName(String name) {
		this.userName = name;
	}

	public void askUserName() {
		System.out.println("PLEASE ENTER YOUR NAME :");
		userName = sc.next();
	}

	public int getPlayerInput() {
		System.out.println("TYPE ROCK, PAPER OR SCISSORS :");

		String input = sc.next();
		input = input.toUpperCase();

		int validatedInput=GameRules.validatePlayerInput(input);

		return validatedInput;
		
		}

	

}
