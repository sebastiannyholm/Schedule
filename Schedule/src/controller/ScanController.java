
package controller;

import java.util.Scanner;

import model.Employee;
import model.Schedule;

public class ScanController {

	Scanner s = new Scanner(System.in);
	
	Schedule schedule;
	Employee user;
	
	public ScanController(Schedule schedule) throws Exception {
		this.schedule = schedule;
		startScreen();
	}
	
	private void startScreen() throws Exception {
		System.out.println("Welcome to the system!");
		System.out.println("You now have the following options:");
		System.out.println("1 - Log in");
		System.out.println("2 - Shut down");
		
		String choose = s.nextLine();
		while (!choose.equals("1") && !choose.equals("2")) {
			System.out.println();
			System.err.println("You didn't choose one of the options, plz choose!");
			choose = s.nextLine();
		}
		
		if (choose.equals("1")) {
			lines();
			System.out.println("You can now log in with your initials!");
			logIn();
		}
		else if (choose.equals("2"))
			System.exit(0);
			
	}

	private void lines() {
		System.out.println();
		System.out.println("*************************************************");
		System.out.println();
	}

	private void logIn() throws Exception {
		schedule.login(s.nextLine());
		while (!schedule.isLoggedIn()) {
			System.out.println();
			System.err.println("Your initials didn't match any of our employees!");
			System.out.println("Plz try again.");
			schedule.login(s.nextLine());
		}
		
		lines();
		user = schedule.getUser();
		loggedIn();
	}
	
	private void loggedIn() throws Exception {
		System.out.println("You are now logged in as " + user.getName());
		System.out.println("You now have the following options:");
		System.out.println("1 - Log out");
		System.out.println("2 - Create project");
		System.out.println("3 - ");
		System.out.println("4 - ");
		System.out.println("5 - ");
		
		String choose = s.nextLine();
		while (!choose.equals("1") && !choose.equals("2")) {
			System.out.println();
			System.err.println("You didn't choose one of the options, plz choose!");
			choose = s.nextLine();
		}
		
		if (choose.equals("1")) {
			logOut();
		}
		else if (choose.equals("2"))
			System.exit(0);
	}

	private void logOut() throws Exception {
		lines();
		System.out.println("Bye bye " + user.getName() + ", see you later!");
		lines();
		schedule.logOut();
		startScreen();
	}
	
}