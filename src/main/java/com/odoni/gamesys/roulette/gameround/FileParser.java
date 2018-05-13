package com.odoni.gamesys.roulette.gameround;

import com.odoni.gamesys.roulette.exception.InvalidFileException;
import com.odoni.gamesys.roulette.model.Player;

import java.util.List;

public interface FileParser {
	public List<Player> retrievePlayersFromFile(String filePath) throws InvalidFileException;
}
