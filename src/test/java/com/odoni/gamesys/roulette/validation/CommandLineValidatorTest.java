package com.odoni.gamesys.roulette.validation;

import com.odoni.gamesys.roulette.model.Player;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class CommandLineValidatorTest {
	private static List<Player> players = new ArrayList<>();

	@BeforeClass
	public static void setUp() {
		players.add(new Player("Player_1", 100.0, 20.0));
		players.add(new Player("Player_2", 50.0, 50.0));
		players.add(new Player("Player_3", 30.0, 200.0));
	}

	@Test
	public void shouldReturnTrueForValidCommandLineWithEvenBet() {
		String commandLine = "Player_1 EVEN 3.0";
		assertTrue(CommandLineValidator.isCommandLineValid(commandLine, players));
	}

	@Test
	public void shouldReturnTrueForValidCommandLineWithOddBet() {
		String commandLine = "Player_2 ODD 3.0";
		assertTrue(CommandLineValidator.isCommandLineValid(commandLine, players));
	}

	@Test
	public void shouldReturnTrueForValidCommandLineWithNumberBet() {
		String commandLine = "Player_1 23 3.0";
		assertTrue(CommandLineValidator.isCommandLineValid(commandLine, players));
	}

	@Test
	public void shouldReturnTrueForExitCommand() {
		String commandLine = "exit";
		assertTrue(CommandLineValidator.isCommandLineValid(commandLine, players));
	}

	@Test
	public void shouldReturnFalseForEmptyCommandLine() {
		String commandLine = "";
		assertFalse(CommandLineValidator.isCommandLineValid(commandLine, players));
	}

	@Test
	public void shouldReturnFalseForBlankCommandLine() {
		String commandLine = "  ";
		assertFalse(CommandLineValidator.isCommandLineValid(commandLine, players));
	}

	@Test
	public void shouldReturnFalseForInvalidCommandLine() {
		String commandLine = "Player_1 EVEN 3.0 80";
		assertFalse(CommandLineValidator.isCommandLineValid(commandLine, players));
	}

	@Test
	public void shouldReturnFalseForInvalidPlayer() {
		String commandLine = "Invalid_Player EVEN 3.0";
		assertFalse(CommandLineValidator.isCommandLineValid(commandLine, players));
	}

	@Test
	public void shouldReturnFalseForInvalidBetType() {
		String commandLine = "Player_1 BLACK 3.0";
		assertFalse(CommandLineValidator.isCommandLineValid(commandLine, players));
	}

	@Test
	public void shouldReturnFalseForInvalidValueBet() {
		String commandLine = "Player_1 EVEN V";
		assertFalse(CommandLineValidator.isCommandLineValid(commandLine, players));
	}
}
