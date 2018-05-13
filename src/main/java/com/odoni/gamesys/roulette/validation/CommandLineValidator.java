package com.odoni.gamesys.roulette.validation;

import com.odoni.gamesys.roulette.model.BetType;
import com.odoni.gamesys.roulette.model.Player;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.List;

public class CommandLineValidator {
	private static final String EXIT_COMMAND = "exit";

	public static boolean isCommandLineValid(String commandLine, List<Player> players) {
		if (StringUtils.isBlank(commandLine) ||
				(!commandLine.equals(EXIT_COMMAND) && commandLine.split(" ").length != 3)) {
			System.out.println("Command not valid.");
			return false;
		}
		if (!commandLine.equals(EXIT_COMMAND)) {
			String[] commands = commandLine.split(" ");
			return isValidName(commands[0], players) && isValidBetType(commands[1]) && isValidBetValue(commands[2]);
		}
		return true;
	}

	private static boolean isValidBetValue(String betValueStr) {
		if (StringUtils.isNotBlank(betValueStr) &&
				NumberUtils.isCreatable(betValueStr)) {
			return true;
		}
		System.out.println("Invalid bet value. Bet value should be a valid number.");
		return false;
	}

	private static boolean isValidBetType(String betTypeStr) {
		if (StringUtils.isNotBlank(betTypeStr) &&
				(betTypeStr.equals(BetType.EVEN.name()) ||
						betTypeStr.equals(BetType.ODD.name()) ||
						(StringUtils.isNumeric(betTypeStr) && isValidNumberBet(betTypeStr)))) {
			return true;
		}
		System.out.println("Invalid bet type. Allowed bet types are: EVEN, ODD or any numeric value.");
		return false;
	}

	private static boolean isValidNumberBet(String betTypeStr) {
		Integer numberBet = Integer.valueOf(betTypeStr);
		return numberBet >= 1 && numberBet <= 36;
	}

	private static boolean isValidName(String name, List<Player> players) {
		if (StringUtils.isNotBlank(name) &&
				players.stream().filter(player -> player.getName().equals(name)).count() > 0) {
			return true;
		}
		System.out.println("Invalid player name. Player not loaded within the players file.");
		return false;
	}
}
