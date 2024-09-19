package hw3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import api.*;

import api.Exit;
import api.Wall;

/**
 * Utility class with static methods for loading game files.
 * 
 * @author Miles Nichols
 */
public class GameFileUtil {
	/**
	 * Loads the file at the given file path into the given game object. When the
	 * method returns the game object has been modified to represent the loaded
	 * game.
	 * 
	 * @param filePath the path of the file to load
	 * @param game     the game to modify
	 */
	public static void load(String filePath, LizardGame game) {
		try {
			Scanner scanner = new Scanner(new File(filePath));
			String firstLine = scanner.nextLine();
			String[] dimensions = firstLine.split("x");
			int width = Integer.parseInt(dimensions[0].trim());
			int height = Integer.parseInt(dimensions[1].trim());
			game.resetGrid(width, height);
			for (int i = 0; i < height; i++) {
				String line = scanner.nextLine();
				for (int j = 0; j < width; j++) {
					char character = line.charAt(j);
					if (character == 'W') {
						Wall wall = new Wall(game.getCell(j, i));
						game.addWall(wall);
					} else if (character == 'E') {
						Exit exit = new Exit(game.getCell(j, i));
						game.addExit(exit);
					}
				}
			}
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.startsWith("L")) {
					String[] segments = line.substring(2).trim().split(" ");
					ArrayList<BodySegment> segmentList = new ArrayList<>();
					Lizard lizard = new Lizard();
					for (String segment : segments) {
						int col = 0;
						int row = 0;
						String[] parts = segment.split(",");
						if (parts.length == 2) {
							col = Integer.parseInt(parts[0].trim());
							row = Integer.parseInt(parts[1].trim());
						}
						BodySegment bodySegment = new BodySegment(lizard, game.getCell(col, row));
						segmentList.add(bodySegment);
					}
					lizard.setSegments(segmentList);
					game.addLizard(lizard);
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}