import java.util.Scanner;

public class BattleShips {
	private static String[][] oceanMap = new String[12][12];
	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		intro();
		createOceanMap();
		deployPlayerShip();
		deployComputerShip();
		gameOver();
		scanner.close();
	}

	public static void intro() {
		System.out.println("**** Welcome to Battle Ships game ****" + "\r\n" + "\r\n  Right now, the sea is empty.");
	}

	public static void createOceanMap() {
		createRow();
		createColumn();
		printMetrix();
	}

	public static void createRow() {
		int length = oceanMap.length;
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < oceanMap[i].length; j++) {
				oceanMap[i][j] = " ";
				if ((i == 0 && j != 0 && j != length - 1) || (i == length - 1 && j != 0 && j != length - 1))
					oceanMap[i][j] = String.valueOf(j - 1);
				if ((i == 0 && j == 0) || (i == length - 1 && j == 0))
					oceanMap[i][j] = "   ";
			}
		}
	}

	public static void createColumn() {
		int side = oceanMap.length;
		for (int i = 0; i < side - 1; i++) {
			for (int j = 0; j < oceanMap[i].length; j++) {
				if (j == 0 && i != 0)
					oceanMap[i][j] = String.valueOf(i - 1) + " |";
				if (j == side - 1 && i != side - 1 && i != 0)
					oceanMap[i][j] = "| " + String.valueOf(i - 1);
			}
		}
	}

	public static void printMetrix() {
		for (int i = 0; i < oceanMap.length; i++) {
			System.out.println();
			for (int j = 0; j < oceanMap[i].length; j++) {
				if (oceanMap[i][j] == "1") {
					System.out.print("@");
				} else if (oceanMap[i][j] == "2" || oceanMap[i][j] == "#")
					System.out.print(" ");
				else
					System.out.print(oceanMap[i][j]);
			}
		}
		System.out.println();
	}

	public static void deployPlayerShip() {
		int count = 0, x, y, arrIdx = 0;
		int[] arr = new int[10];
		while (count < 5) {
			System.out.print("\r\n" + "Enter X coordinate for your ship: ");
			x = scanner.nextInt();
			System.out.print("Enter Y coordinate for your ship: ");
			y = scanner.nextInt();
			if (checkIfValidToPlaceShip(x + 1, y + 1)) {
				oceanMap[x + 1][y + 1] = "1";
				arr[arrIdx++] = x;
				arr[arrIdx++] = y;
				count++;
			}
		}
		System.out.println("\r\n" + "Deploy your ships:");
		arrIdx = 1;
		for (int i = 0; i < arr.length; i++) {
			if (i % 2 == 0)
				System.out.println("Enter X coordinate for your " + arrIdx + ". ship: " + arr[i]);
			else {
				System.out.println("Enter Y coordinate for your " + arrIdx + ". ship: " + arr[i]);
				arrIdx++;
			}
		}
	}

	public static boolean checkIfValidToPlaceShip(int x, int y) {
		if (x < 1 || x > 10 || y < 1 || y > 10) {
			System.out.println("\r\n" + "you can’t place ships outside the 10 by 10 grid");
			return false;
		}
		if (oceanMap[x][y] != " ") {
			System.out.println("\r\n" + "you can NOT place two or more ships on the same location");
			return false;
		}
		return true;
	}

	public static void deployComputerShip() {
		int count = 0, arrIdx = 0, x, y;
		int[] arr = new int[10];
		while (count < 5) {
			x = (int) (Math.random() * 10) + 1;
			y = (int) (Math.random() * 10) + 1;
			if (oceanMap[x][y] == " ") {
				oceanMap[x][y] = "2";
				arr[arrIdx++] = x;
				arr[arrIdx++] = y;
				count++;
			}
		}
		System.out.println("\r\n" + "Computer is deploying ships");
		for (int i = 1; i <= 5; i++) {
			System.out.println(i + ". ship DEPLOYED");
		}
	}

	public static void battle() {
		int Xguess = 1, Yguess = 1;
		System.out.println("\r\n" + "YOUR TURN");
		System.out.print("Enter X coordinate ");
		Xguess += scanner.nextInt();
		System.out.print("Enter Y coordinate ");
		Yguess += scanner.nextInt();
		String result = oceanMap[Xguess][Yguess];

		switch (result.charAt(0)) {
		case '1':
			System.out.println("Oh no, you sunk your own ship :(");
			oceanMap[Xguess][Yguess] = "x";
			break;
		case '2':
			System.out.println("Boom! You sunk the ship!");
			oceanMap[Xguess][Yguess] = "!";
			break;
		default:
			System.out.println("Sorry, you missed");
			if (result == " ")
				oceanMap[Xguess][Yguess] = "-";
			break;
		}

		System.out.println("\r\n" + "COMPUTER'S TURN");
		do {
			Xguess = (int) (Math.random() * 10) + 1;
			Yguess = (int) (Math.random() * 10) + 1;
			result = oceanMap[Xguess][Yguess];
		} while (result == "#");

		switch (result.charAt(0)) {
		case '1':
			System.out.println("The Computer sunk one of your ships!");
			oceanMap[Xguess][Yguess] = "x";
			break;
		case '2':
			System.out.println("The Computer sunk one of its own ships");
			oceanMap[Xguess][Yguess] = "!";
			break;
		default:
			System.out.println("Computer missed");
			if (result == " ")
				oceanMap[Xguess][Yguess] = "#";
			break;
		}
		printMetrix();
	}

	public static int currentShipsStatus() {
		int compShip = 0, playerShip = 0;
		for (int i = 0; i < oceanMap.length; i++) {
			for (int j = 0; j < oceanMap[i].length; j++) {
				if (oceanMap[i][j] == "1") 
					playerShip++;
				if (oceanMap[i][j] == "2") 
					compShip++;
			}
		}
		System.out.println("\r\n" + "Your ships: " + playerShip + " | Computer ships: " + compShip);
		if (playerShip == 0)
			return 2;
		if (compShip == 0)
			return 1;
		return 0;
	}

	public static void gameOver() {
		int win = 0;
		while (win == 0) {
			battle();
			win = currentShipsStatus();
		}
		if (win == 1)
			System.out.println("\r\n" + "Hooray! you win the battle :)");
		if (win == 2)
			System.out.println("\r\n" + "The computer won the battle :(");
	}
}
