package com.skilldistillery.filmquery.app;

import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
//		app.test();
		app.launch();
	}

	private void test() {
//		Film film = db.findFilmById(959);
//		System.out.println(film);
//		Actor actor = db.findActorById(3);
//		System.out.println(actor);
//		List<Actor> actors = db.findActorsByFilmId(3);
//		System.out.println(actors);
		List<Film> films = db.findFilmBySearch("holes");
		System.out.println(films);
	}

	private void launch() {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) {
		
		int choice;
		do {
			userMenu();
			choice = input.nextInt();
			while (choice < 1 || choice > 3) {
				System.out.println("Please select 1-3.");
				userMenu();
				choice = input.nextInt();
			}
			if (choice == 1) {
				System.out.println("Please enter the Film ID: ");
				int filmId = input.nextInt();
				Film film = db.findFilmById(filmId);
				while (film == null) {
					System.out.println("Sorry, the film was not found.\nPlease try again.");
					System.out.println("Please enter the Film ID: ");
					filmId = input.nextInt();
					film = db.findFilmById(filmId);
				}
				System.out.println(film);
			}
			if (choice == 2) {
				System.out.println("Please enter a search keyword: ");
				String keyword = input.next();
				List<Film> films = db.findFilmBySearch(keyword);
				while (films.isEmpty()) {
					System.out.println("Sorry, no results were found.\nPlease try again.");
					System.out.println("Please enter a search keyword: ");
					keyword = input.next();
					films = db.findFilmBySearch(keyword);
				}
				System.out.println(films);
			}
			if (choice == 3) {
				System.out.println("Good bye!");
			} 
			
		} while (choice != 3);
	}

	private void userMenu() {
		System.out.println("Please choose the number of the corresponding menu option. ");
		System.out.println("1. Look up a film by its id.");
		System.out.println("2. Look up a film by a search keyword.");
		System.out.println("3. Exit the application.");
	}
	
//	private void subMenu() {
//		System.out.println("1. Return to the main menu.");
//		System.out.println("2. View all film details.");
//	}

}
