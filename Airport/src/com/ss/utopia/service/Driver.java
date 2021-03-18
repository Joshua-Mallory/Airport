package src.com.ss.utopia.service;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Driver {

	public static void main(String[] args) throws Exception {
		// ConnectSetup c = new ConnectSetup();
		// c.getConnection();
		// Driver d = new Driver();
		menuStart();
	}

	public static void menuStart() throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.println("Which category of a user are you?\n1) Employee\n2) Administrator\n3) Traveler");
		String cat = sc.nextLine();
		Integer categ = Integer.parseInt(cat);
		String placeholder = null;
		switch (categ) {
		case 1:
			employeeMenu();
			break;
		case 2:
			placeholder = "Administrator";
			break;
		case 3:
			placeholder = "Traveler";
			break;
		}

		System.out.println(placeholder);
	}

	public static void employeeMenu() throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.println("1)Enter Flights You Manage\n2)Quit to previous ");
		String cat = sc.nextLine();
		Integer categ = Integer.parseInt(cat);
		switch (categ) {
		case 1:
			employeeMenu2();
			break;
		case 2:
			menuStart();
			break;
		}
	}

	public static void employeeMenu2() throws Exception {
		Scanner sc = new Scanner(System.in);
		ConnectSetup c = new ConnectSetup();
		List<Object> flights = c.getConnection();
		for (int i = 0; i < flights.size(); i++) {
			Object[] temp = (Object[]) flights.get(i);
			// create custom object
			System.out.println((i + 1) + " " + Arrays.toString(temp));
			// System.out.println((String) (flights.get(i)));
		}

		String cat = sc.nextLine();
		Integer categ = Integer.parseInt(cat);

		switch (categ) {
		case 1:
			break;
		case 2:
			menuStart();
			break;
		}
		/*
		 * Welcome to the Utopia Airlines Management System. Which category of a user
		 * are you1)Employee2)Administrator3)Traveler<take input>
		 */
	}
}
