package edu.ncsu.csc316.social.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * UI that prompts whether you want to display connections by
 * person, platform, or you want to exit.
 * @author Arnold Elamthuruthil
 */
public class SocialMediaManagerUI {
	/**
	 * Main method where the report manager is ran.
	 * @param args command line arguments 
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
        String peopleFile;
        String connectionFile;

        do {
            System.out.print("Enter the people file name: ");
            peopleFile = "input/" + scanner.nextLine();
        } while (!isValidFile(peopleFile));

        do {
            System.out.print("Enter the connection file name: ");
            connectionFile = "input/" + scanner.nextLine();
        } while (!isValidFile(connectionFile));

        ReportManager reportManager;
        try {
            reportManager = new ReportManager(peopleFile, connectionFile);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
            return;
        }

        int choice;
        do {
            System.out.println("\nChoose an option:");
            System.out.println("1. Display connections by person");
            System.out.println("2. Display connections by platform");
            System.out.println("3. Exit SocialMediaManager");
            System.out.print("Enter : ");
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        displayConnectionsByPerson(reportManager);
                        break;
                    case 2:
                        displayConnectionsByPlatform(reportManager);
                        break;
                    case 3:
                        System.out.println("Exiting.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a number 1-3.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                choice = 0;
            }
        } while (true);
    }

    private static void displayConnectionsByPerson(ReportManager reportManager) {
        String connectionsByPerson = reportManager.getConnectionsByPerson();
        System.out.println(connectionsByPerson);
    }

    private static void displayConnectionsByPlatform(ReportManager reportManager) {
        String connectionsByPlatform = reportManager.getConnectionsByPlatform();
        System.out.println(connectionsByPlatform);
    }

    private static boolean isValidFile(String fileName) {
    	if (fileName.length() == 6) {
    		return false;
    	}
        File file = new File(fileName);
        return file.exists();
    }
}