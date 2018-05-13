package com.odoni.gamesys.roulette;

import com.odoni.gamesys.roulette.exception.InvalidFileException;
import com.odoni.gamesys.roulette.model.Player;
import com.odoni.gamesys.roulette.validation.CommandLineValidator;
import com.odoni.gamesys.roulette.gameround.FileParser;
import com.odoni.gamesys.roulette.gameround.FileParserDefault;
import com.odoni.gamesys.roulette.gameround.GameRoundThread;
import com.odoni.gamesys.roulette.model.Bet;
import com.odoni.gamesys.roulette.model.BetType;

import java.util.List;
import java.util.Scanner;

public class Application {

	private static final String ENTER_THE_PLAYERS_FILE = "Enter players file:";

	public static void main(String... args) {
		Scanner in = new Scanner(System.in);
		boolean invalidFile = true;
		List<Player> players = null;

		System.out.println(ENTER_THE_PLAYERS_FILE);
		while (invalidFile) {
			String filePath = in.nextLine();
			FileParser fileParser = new FileParserDefault();
			try {
				players = fileParser.retrievePlayersFromFile(filePath);
				invalidFile = false;
			} catch (InvalidFileException e) {
				System.out.println(e.getMessage());
				System.out.println("Try again.");
				System.out.println(ENTER_THE_PLAYERS_FILE);
			}
		}

		System.out.println("Starting game. Place your bets!");

		GameRoundThread gameRoundThread = new GameRoundThread(players);
		gameRoundThread.start();

		while (true) {
			String commandLine = in.nextLine();
			if (CommandLineValidator.isCommandLineValid(commandLine, players)) {
				placeBetFromLine(commandLine, gameRoundThread);
			}
			System.out.println("Next bet:");
		}
	}

	private static void placeBetFromLine(String commandLine, GameRoundThread gameRoundThread) {
		if (commandLine.equals("exit")) {
			System.exit(0);
		}
		String[] commands = commandLine.split(" ");
		Bet bet = createBetFromCommandLine(commands[1], commands[2]);
		gameRoundThread.placeBet(commands[0], bet);
	}

	private static Bet createBetFromCommandLine(String betTypeStr, String betValueStr) {
		Bet bet = new Bet();
		switch (betTypeStr) {
			case "EVEN" :
				bet.setBetType(BetType.EVEN);
				break;
			case "ODD" :
				bet.setBetType(BetType.ODD);
				break;
			default :
				bet.setBetType(BetType.NUMBER);
				bet.setBetNumber(Integer.valueOf(betTypeStr));
				break;
		}
		bet.setValue(Double.valueOf(betValueStr));
		return bet;
	}
}
