package com.mavericks.app;

import java.util.Scanner;

public class GameRules {

	public static final int ROCK = 1;
	public static final int PAPER = 2;
	public static final int SCISSORS = 3;


	public static void displayPlayerInput(String who, int s) {
		switch (s) {
		case GameRules.ROCK:
			System.out.print(who + " SELECTED ROCK ");
			break;
		case GameRules.PAPER:
			System.out.print(who + " SELECTED PAPER ");
			break;
		case GameRules.SCISSORS:
			System.out.print(who + " SELECTED SCISSORS ");
			break;
		default:
			break;
		}
	}

	public static int compareSelections(int userSelection, int computerSelection) {
		if(userSelection==computerSelection) return 0;
		switch (userSelection) {
		case ROCK:
			return (computerSelection == SCISSORS ? 1 : -1);

		case PAPER:
			return (computerSelection == ROCK ? 1 : -1);

		case SCISSORS:
			return (computerSelection == PAPER ? 1 : -1);
		}
		return 1000;
	}
	
	public static int validatePlayerInput(String playerChoice) {
		if("ROCK".equals(playerChoice)) {
			return GameRules.ROCK;			
		}
		else if("PAPER".equals(playerChoice)) {
			return GameRules.PAPER;
		}
		else if("SCISSORS".equals(playerChoice)) {
			return GameRules.SCISSORS;
		}
		return 0;
	}
	
	
	public static boolean playAgain() {
		Scanner sc = new Scanner(System.in);
		sc=new Scanner(System.in);
		System.out.print("DO YOU WANT TO PLAY AGAIN ? ");
		String userInput = sc.nextLine();
		userInput = userInput.toUpperCase();
		return userInput.charAt(0) == 'Y';
	}
	
}

