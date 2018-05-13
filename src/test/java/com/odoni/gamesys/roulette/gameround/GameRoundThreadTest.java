package com.odoni.gamesys.roulette.gameround;

import com.odoni.gamesys.roulette.model.Bet;
import com.odoni.gamesys.roulette.model.BetType;
import com.odoni.gamesys.roulette.model.Player;
import com.odoni.gamesys.roulette.model.ResultRoulette;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameRoundThreadTest {

	private List<Player> players;

	@Before
	public void setUp() {
		players = Arrays.asList(new Player("Player_1", 1.0, 2.0),
				new Player("Player_2", 3.0, 4.0));
	}

	@Test
	public void shouldPlacePlayerBetSuccessfully() {
		GameRoundThread gameRoundThread = new GameRoundThread(players);
		gameRoundThread.placeBet("Player_1", new Bet(BetType.EVEN,2.0, null));
		assertEquals(1, gameRoundThread.getBets().get("Player_1").size());
		assertEquals(BetType.EVEN, gameRoundThread.getBets().get("Player_1").get(0).getBetType());
		assertEquals(Double.valueOf(2.0), gameRoundThread.getBets().get("Player_1").get(0).getValue());
	}

	@Test
	public void shouldPlaceSecondPlayerBetSuccessfully() {
		GameRoundThread gameRoundThread = new GameRoundThread(players);
		gameRoundThread.placeBet("Player_1", new Bet(BetType.EVEN,2.0, null));
		gameRoundThread.placeBet("Player_1", new Bet(BetType.ODD,3.0, null));
		assertEquals(2, gameRoundThread.getBets().get("Player_1").size());
		assertEquals(BetType.EVEN, gameRoundThread.getBets().get("Player_1").get(0).getBetType());
		assertEquals(Double.valueOf(2.0), gameRoundThread.getBets().get("Player_1").get(0).getValue());
		assertEquals(BetType.ODD, gameRoundThread.getBets().get("Player_1").get(1).getBetType());
		assertEquals(Double.valueOf(3.0), gameRoundThread.getBets().get("Player_1").get(1).getValue());
	}

	@Test
	public void shouldResetBetSuccessfully() {
		GameRoundThread gameRoundThread = new GameRoundThread(players);
		gameRoundThread.placeBet("Player_1", new Bet(BetType.EVEN,2.0, null));
		gameRoundThread.resetBets();
		assertEquals(0, gameRoundThread.getBets().size());
	}

	@Test
	public void shouldEvaluateSingleEvenBetWinSuccessfully() {
		GameRoundThread gameRoundThread = new GameRoundThread(players);
		gameRoundThread.placeBet("Player_1", new Bet(BetType.EVEN,2.0, null));
		List<ResultRoulette> result = gameRoundThread.evaluateResultForPlayer("Player_1",
				gameRoundThread.getBets().get("Player_1"),
				22);
		assertEquals(Double.valueOf(2.0), result.get(0).getAmountBet());
		assertEquals(Double.valueOf(4.0), result.get(0).getWinnings());
		assertEquals(BetType.EVEN.name(), result.get(0).getBetType());
		assertEquals("WIN", result.get(0).getOutcome());
	}

	@Test
	public void shouldEvaluateSingleEvenBetLoseSuccessfully() {
		GameRoundThread gameRoundThread = new GameRoundThread(players);
		gameRoundThread.placeBet("Player_1", new Bet(BetType.EVEN,4.0, null));

		List<ResultRoulette> result = gameRoundThread.evaluateResultForPlayer("Player_1",
				gameRoundThread.getBets().get("Player_1"),
				3);

		assertEquals(Double.valueOf(4.0), result.get(0).getAmountBet());
		assertEquals(Double.valueOf(0.0), result.get(0).getWinnings());
		assertEquals(BetType.EVEN.name(), result.get(0).getBetType());
		assertEquals("LOSE", result.get(0).getOutcome());
	}

	@Test
	public void shouldEvaluateSingleOddBetWinSuccessfully() {
		GameRoundThread gameRoundThread = new GameRoundThread(players);
		gameRoundThread.placeBet("Player_1", new Bet(BetType.ODD,2.0, null));
		List<ResultRoulette> result = gameRoundThread.evaluateResultForPlayer("Player_1",
				gameRoundThread.getBets().get("Player_1"),
				23);
		assertEquals(Double.valueOf(2.0), result.get(0).getAmountBet());
		assertEquals(Double.valueOf(4.0), result.get(0).getWinnings());
		assertEquals(BetType.ODD.name(), result.get(0).getBetType());
		assertEquals("WIN", result.get(0).getOutcome());
	}

	@Test
	public void shouldEvaluateSingleOddBetLoseSuccessfully() {
		GameRoundThread gameRoundThread = new GameRoundThread(players);
		gameRoundThread.placeBet("Player_1", new Bet(BetType.ODD,4.0, null));

		List<ResultRoulette> result = gameRoundThread.evaluateResultForPlayer("Player_1",
				gameRoundThread.getBets().get("Player_1"),
				2);

		assertEquals(Double.valueOf(4.0), result.get(0).getAmountBet());
		assertEquals(Double.valueOf(0.0), result.get(0).getWinnings());
		assertEquals(BetType.ODD.name(), result.get(0).getBetType());
		assertEquals("LOSE", result.get(0).getOutcome());
	}

	@Test
	public void shouldEvaluateSingleNumberBetWinSuccessfully() {
		GameRoundThread gameRoundThread = new GameRoundThread(players);
		gameRoundThread.placeBet("Player_1", new Bet(BetType.NUMBER,2.0, 23));
		List<ResultRoulette> result = gameRoundThread.evaluateResultForPlayer("Player_1",
				gameRoundThread.getBets().get("Player_1"),
				23);
		assertEquals(Double.valueOf(2.0), result.get(0).getAmountBet());
		assertEquals(Double.valueOf(72.0), result.get(0).getWinnings());
		assertEquals("23", result.get(0).getBetType());
		assertEquals("WIN", result.get(0).getOutcome());
	}

	@Test
	public void shouldEvaluateSingleNumberBetLoseSuccessfully() {
		GameRoundThread gameRoundThread = new GameRoundThread(players);
		gameRoundThread.placeBet("Player_1", new Bet(BetType.NUMBER,4.0, 23));

		List<ResultRoulette> result = gameRoundThread.evaluateResultForPlayer("Player_1",
				gameRoundThread.getBets().get("Player_1"),
				24);

		assertEquals(Double.valueOf(4.0), result.get(0).getAmountBet());
		assertEquals(Double.valueOf(0.0), result.get(0).getWinnings());
		assertEquals("23", result.get(0).getBetType());
		assertEquals("LOSE", result.get(0).getOutcome());
	}

	@Test
	public void shouldRunRouletteAndGenerateRightNumber() {
		GameRoundThread gameRoundThread = new GameRoundThread(players);
		Integer rouletteNumber = gameRoundThread.runRoulette();
		assertTrue(rouletteNumber >= 0 && rouletteNumber <=36);
	}

}
