package be.swsb.coderetreat.util;

import java.util.Scanner;

public class Helper {
    private final Scanner scanner;

    public Helper() {
        this.scanner = new Scanner(System.in);
    }

    public int getIntInput(String message) throws IllegalArgumentException {
        System.out.println(message);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    public String getDirectionInput(String message) {
        System.out.println(message);
        String input = scanner.nextLine();
        while (true) {
            if (input.equalsIgnoreCase("h") || input.equalsIgnoreCase("v")) {
                return input.toLowerCase();
            } else {
                System.out.println("Invalid input. Please enter 'h' or 'v'.");
                input = scanner.nextLine();
            }
        }
    }

    public String getStringInput(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }

    public static void SwitchTurn() {
        System.out.println("Switching players... look away so you don't see your opponents screen!");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\n".repeat(50));
    }
}
