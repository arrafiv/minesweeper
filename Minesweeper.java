import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/*
 * This is a simple Minesweeper game using console
 */
public class Minesweeper {
	private static String[][] maze;
	private static int jmlBomb = 0;
	private static int size = 0;

	//Main Class
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Insert Maze Size (n): ");
		size = Integer.parseInt(br.readLine());

		System.out.println("Number of Bombs: ");
		int bomb = Integer.parseInt(br.readLine());
		ArrayList<String> listBomb = new ArrayList<String>();

		//Save bomb
		System.out.println("Bomb Coordinate (m,n): ");
		for (int i = 0; i < bomb; i++) {
			listBomb.add(br.readLine());
		}

		maze = constructMaze(size, listBomb);

		System.out.println("LET'S PLAY!");
		
		//print the maze
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (maze[i][j].equals("@")) {
					System.out.printf("#");
				} else {
					System.out.printf(maze[i][j]);
				}
			}
			System.out.println();
		}

		String line = "";

		String selectedTile = "";
		
		System.out.println("Select one tile coordinate (m,n) : ");
		while (!(line = br.readLine()).isEmpty()) {
			selectedTile = line;
			String[] split = selectedTile.split(",");
			int x = Integer.parseInt(split[0]) - 1;
			int y = Integer.parseInt(split[1]) - 1;

			if (listBomb.contains(line)) {
				//if the player select the bomb, it's the end of the game.
				System.out.println("IT'S A BOMB! GAME OVER!");
				break;
			} else {
				findIt(x, y);

				//print the maze after selecting on tile
				for (int i = 0; i < size; i++) {
					for (int j = 0; j < size; j++) {
						if (maze[i][j].equals("@")) {
							//Hide Bomb in Maze
							System.out.printf("#");
						} else {
							System.out.printf(maze[i][j]);
						}

					}
					System.out.println();
				}

				if (countClosed() == jmlBomb) {
					System.out.println("YOU WIN");
					break;
				}
			}
		}
	}

	/*
	 * Method for checking if the bomb as same amount as the closed tile
	 */
	public static int countClosed() {
		int count = 0;

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (maze[i][j].equals("#")) {
					count += 1;
				}
			}
		}
		return count;
	}

	/*
	 * Method to generate the maze
	 */
	public static String[][] constructMaze(int size, List<String> bomb) {
		String[][] maze = new String[size][size];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				maze[i][j] = "#";
			}
		}

		for (int i = 0; i < bomb.size(); i++) {
			String[] split = bomb.get(i).split(",");
			int x = Integer.parseInt(split[0]) - 1;
			int y = Integer.parseInt(split[1]) - 1;
			maze[x][y] = "@";
		}

		return maze;
	}

	/*
	 * Method for opening all tile around the selected tile
	 */
	public static void findIt(int x, int y) {
		boolean found = false;
		int jmlBombDisekitar = 0;

		//Check the bomb around selectedTile
		if (x != 0 && maze[x - 1][y] == "@") {
			//up
			jmlBombDisekitar += 1;
			found = true;
		}

		if (x != 0 && y != size - 1 && maze[x - 1][y + 1] == "@") {
			//up-right
			jmlBombDisekitar += 1;
			found = true;
		}

		if (y != size - 1 && maze[x][y + 1] == "@") {
			//right
			jmlBombDisekitar += 1;
			found = true;
		}

		if (x != size - 1 && y != size - 1 && maze[x + 1][y + 1] == "@") {
			//right-down
			jmlBombDisekitar += 1;
			found = true;
		}

		if (x != size - 1 && maze[x + 1][y] == "@") {
			//down
			jmlBombDisekitar += 1;
			found = true;
		}

		if (x != size - 1 && y != 0 && maze[x + 1][y - 1] == "@") {
			//left-down
			jmlBombDisekitar += 1;
			found = true;
		}

		if (y != 0 && maze[x][y - 1] == "@") {
			//left
			jmlBombDisekitar += 1;
			found = true;
		}

		if (x != 0 && y != 0 && maze[x - 1][y - 1] == "@") {
			//up-left
			jmlBombDisekitar += 1;
			found = true;
		}

		if (found == true) {
			//mark number of bomb around selectedTile
			maze[x][y] = "" + jmlBombDisekitar;
			return;
		} else if (maze[x][y] == ".") {
			return;
		} else if (maze[x][y] == "1" || maze[x][y] == "2" || maze[x][y] == "3" || maze[x][y] == "4" || maze[x][y] == "5"
				|| maze[x][y] == "6" || maze[x][y] == "7" || maze[x][y] == "8") {
			//maximum number of bomb around one tile is 8
			return;
		} else {
			//mark selected tile with "." if no bomb around
			maze[x][y] = ".";
		}

		if (x != 0) {
			//up
			findIt(x - 1, y);
		}
		if (x != 0 && y != size - 1) {
			//up-right
			findIt(x - 1, y + 1);
		}
		if (y != size - 1) {
			//right
			findIt(x, y + 1);
		}
		if (x != size - 1 && y != size - 1) {
			//right-down
			findIt(x + 1, y + 1);
		}
		if (x != size - 1) {
			//down
			findIt(x + 1, y);
		}
		if (x != size - 1 && y != 0) {
			//left-down
			findIt(x + 1, y - 1);
		}
		if (y != 0) {
			//left
			findIt(x, y - 1);
		}
		if (x != 0 && y != 0) {
			//up-left
			findIt(x - 1, y - 1);
		}
	}
}