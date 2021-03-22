package com.ss.utopia.service;

import java.sql.Time;
import java.util.Scanner;

import menu.AdminMenu;
import menu.EmployeeMenu;
import menu.TravelMenu;

public class Driver {

	public static void main(String[] args) {
		try {
			menuStart();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Some Error Has Occured. Most likely a database sql error.");
		}
	}

	public static void menuStart() throws Exception {
		System.out.println("Which category of a user are you?\n1) Employee\n2) Administrator\n3) Traveler");
		Integer categ = scanHandleInt();
		switch (categ) {
		case 1:
			EmployeeMenu.employeeMenu();
			break;
		case 2:
			AdminMenu.adminMenu();
			break;
		case 3:
			TravelMenu.travelLogin();
			break;
		}
	}

	public static Integer scanHandleInt() {
		Scanner sc = new Scanner(System.in);
		String cat = sc.nextLine();
		Integer categ = Integer.parseInt(cat);
		return categ;
	}

	public static String scanCheckNA() {
		Scanner sc = new Scanner(System.in);
		String cat = sc.nextLine();
		return cat;
	}

	public static Time scanHandleTime(String cat) {
		String[] temp = cat.split(":");
		Time categ = new Time(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));
		return categ;
	}

	public static String scanHandleDate(String cat) {
		return cat;
	}

	public static String scanHandleString() {
		Scanner sc = new Scanner(System.in);
		String cat = sc.nextLine();
		return cat;
	}

	public static String[] scanHandleString(String cat) {
		return cat.split(" ", 2);
	}
}
