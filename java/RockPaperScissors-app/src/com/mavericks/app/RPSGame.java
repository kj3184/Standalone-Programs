package com.mavericks.app;

import java.util.Scanner;

public class RPSGame {

	private Player player1;
	private Player player2;
	
	//Initialise the score variables and other stats
	
	private int playerScore;
	private int computerScore;
	private int tieScore;
	private int numberOfGames;
	
	Scanner scanner = new Scanner(System.in);

	public RPSGame() {
		playerScore = 0;
		computerScore = 0;
		numberOfGames = 0;
		tieScore=0;
	}
	public static void main(String[] args) {

		RPSGame rps = new RPSGame();
		rps.choosePlayMode();
		
	}
	

	public void choosePlayMode() {
		System.out.println("SELECT PLAY MODE :\n");
		System.out.println("1. PLAYER VS COMPUTER \n");
		System.out.println("2. COMPUTER Vs COMPUTER \n");
		 
		 int playmodeInput = Integer.parseInt(scanner.next());
		        
		        switch (playmodeInput) {
		            case 1:
		            	player1 = new Human();
		        		player2 = new Computer();
		        		playerVsComputerGame();
		                break;
		            case 2:
		            	player1 = new Computer();
		        		player2 = new Computer();
		        		computerVsComputerGame();
		                break;
		            default :
		            	System.out.println("INVALID PLAY MODE");
		            	choosePlayMode();
		                break;
		        }
		        
	
		
	}
	
	public void playerVsComputerGame() {
		getplayerName();
		startGame();
	}
	
	public void computerVsComputerGame() {
		startGame();
	}
	
	public void startGame() {
		int player1input = player1.getPlayerInput();
		
		
		if(player1input==0)
		{
			System.out.println("INVALID CHOICE : RESTARTING GAME AGAIN");
			startGame();
		}
		
		int player2input = player2.getPlayerInput();
		
		if(player1 instanceof Human) {
			GameRules.displayPlayerInput(((Human) player1).getUserName(),player1input);
		}
		else {
			GameRules.displayPlayerInput("COMPUTER",player1input);
		}
		
		GameRules.displayPlayerInput("COMPUTER", player2input);
		
		
		int compareResult=GameRules.compareSelections(player1input, player2input);
		switch (compareResult) {
		case 0: // Tie
			System.out.println("TIE!");
			tieScore++;
			break;
		case 1: // player wins
			if(player1 instanceof Human) {
			    System.out.println(((Human) player1).getUserName().toUpperCase()+ " BEATS " + "COMPUTER;" +"\nYOU WON!");
			}
			else {
				System.out.println("COMPUTER BEATS COMPUTER;" +"\nCOMPUTER1 WON!");
			}
			playerScore++;
			break;
		case -1: // Computer wins
			if(player1 instanceof Human) {
				System.out.println("COMPUTER BEATS "+ ((Human) player1).getUserName().toUpperCase()+";\nYOU LOST!");
			}
			else {
				System.out.println("COMPUTER BEATS COMPUTER ;\nCOMPUTER2 WON!");	
			}
			computerScore++;
			break;
		}
		numberOfGames++;
		if(playerScore==5)
		{
			if(player1 instanceof Human) {
				System.out.println(((Human) player1).getUserName().toUpperCase()+" HAS WON THE GAME.............");
			}
			else {
				System.out.println("COMPUTER HAS WON THE GAME.............");	
			}
			new RPSGame();
		}
		if(computerScore==5)
		{
			System.out.println("COMPUTER HAS WON THE GAME............");
			new RPSGame();
		}
		// Ask the player to play again
		if (GameRules.playAgain()) {
			System.out.println();
			startGame();
		} else {
			printStats();
		}
	}

	
	//helps to get the users name
	public void getplayerName() {
    	((Human) player1).askUserName();
	}
	
	
	//Prints the result.
	public void printStats()
	{
		System.out.println("NUMBER OF GAMES PLAYED IS :"+numberOfGames);
		if(player1 instanceof Human) {
			System.out.println(((Human) player1).getUserName().toUpperCase()+"'S SCORE :"+playerScore);
		}else {
			System.out.println("COMPUTER'S SCORE :"+playerScore);
		}
		System.out.println("COMPUTER'S SCORE :"+computerScore);
		System.out.println("NO. OF TIES :"+tieScore);
		System.exit(0);
		
	}
	
	
}