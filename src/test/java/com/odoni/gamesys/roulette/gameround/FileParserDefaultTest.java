package com.odoni.gamesys.roulette.gameround;

import com.odoni.gamesys.roulette.exception.InvalidFileException;
import com.odoni.gamesys.roulette.model.Player;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest(FileParserDefault.class)
public class FileParserDefaultTest {

	@Mock
	Path path;

	@Test(expected = InvalidFileException.class)
	public void shouldThrowExceptionForEmptyPath() throws InvalidFileException {
		FileParserDefault fileParserDefault = new FileParserDefault();
		fileParserDefault.retrievePlayersFromFile("");
	}

	@Test
	public void shouldExtractPlayersWithoutStatsFromFileSuccessfully() throws InvalidFileException, IOException {
		FileParserDefault fileParserDefault = new FileParserDefault();

		PowerMockito.mockStatic(Paths.class);
		when(Paths.get(any(String.class))).thenReturn(path);

		StringBuilder playersFileContent = new StringBuilder();
		playersFileContent.append("player_1");
		playersFileContent.append(System.lineSeparator());
		playersFileContent.append("player_2");
		BufferedReader playersFile = new BufferedReader(new StringReader(playersFileContent.toString()));

		PowerMockito.mockStatic(Files.class);
		when(Files.newBufferedReader(path)).thenReturn(playersFile);

		List<Player> players = fileParserDefault.retrievePlayersFromFile("/test/folder/players.txt");
		assertEquals(2, players.size());
		assertEquals("player_1", players.get(0).getName());
		assertEquals("player_2", players.get(1).getName());
	}

	@Test
	public void shouldExtractPlayersWithStatsFromFileSuccessfully() throws InvalidFileException, IOException {
		FileParserDefault fileParserDefault = new FileParserDefault();

		PowerMockito.mockStatic(Paths.class);
		when(Paths.get(any(String.class))).thenReturn(path);

		StringBuilder playersFileContent = new StringBuilder();
		playersFileContent.append("player_1,1.0,2.0");
		playersFileContent.append(System.lineSeparator());
		playersFileContent.append("player_2,3.0,4.0");
		BufferedReader playersFile = new BufferedReader(new StringReader(playersFileContent.toString()));

		PowerMockito.mockStatic(Files.class);
		when(Files.newBufferedReader(path)).thenReturn(playersFile);

		List<Player> players = fileParserDefault.retrievePlayersFromFile("/test/folder/players.txt");
		assertEquals(2, players.size());
		assertEquals("player_1", players.get(0).getName());
		assertEquals(Double.valueOf(1.0), players.get(0).getTotalWin());
		assertEquals(Double.valueOf(2.0), players.get(0).getTotalBet());

		assertEquals("player_2", players.get(1).getName());
		assertEquals(Double.valueOf(3.0), players.get(1).getTotalWin());
		assertEquals(Double.valueOf(4.0), players.get(1).getTotalBet());
	}

	@Test(expected = InvalidFileException.class)
	public void shouldThrowAnExceptionForEmptyFile() throws InvalidFileException, IOException {
		FileParserDefault fileParserDefault = new FileParserDefault();

		PowerMockito.mockStatic(Paths.class);
		when(Paths.get(any(String.class))).thenReturn(path);

		BufferedReader playersFile = new BufferedReader(new StringReader(""));

		PowerMockito.mockStatic(Files.class);
		when(Files.newBufferedReader(path)).thenReturn(playersFile);

		fileParserDefault.retrievePlayersFromFile("/test/folder/players.txt");
	}

	@Test(expected = InvalidFileException.class)
	public void shouldThrowAnExceptionForInvalidFileContent() throws InvalidFileException, IOException {
		FileParserDefault fileParserDefault = new FileParserDefault();

		PowerMockito.mockStatic(Paths.class);
		when(Paths.get(any(String.class))).thenReturn(path);

		StringBuilder playersFileContent = new StringBuilder();
		playersFileContent.append("player_1,1.0,A");
		playersFileContent.append(System.lineSeparator());
		playersFileContent.append("player_2,B,4.0");
		BufferedReader playersFile = new BufferedReader(new StringReader(playersFileContent.toString()));

		PowerMockito.mockStatic(Files.class);
		when(Files.newBufferedReader(path)).thenReturn(playersFile);

		fileParserDefault.retrievePlayersFromFile("/test/folder/players.txt");
	}

	@Test(expected = InvalidFileException.class)
	public void shouldThrowAnExceptionForFileNotFound() throws InvalidFileException, IOException {
		FileParserDefault fileParserDefault = new FileParserDefault();

		PowerMockito.mockStatic(Paths.class);
		when(Paths.get(any(String.class))).thenReturn(path);

		PowerMockito.mockStatic(Files.class);
		when(Files.newBufferedReader(path)).thenThrow(FileNotFoundException.class);

		fileParserDefault.retrievePlayersFromFile("/test/folder/players.txt");
	}

}
