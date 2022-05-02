package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {

	private final String user = "student";
	private final String pass = "student";
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=US/Mountain";

	public DatabaseAccessorObject() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Film findFilmById(int filmId) {
		Film film = null;
		List<Actor> actors = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT film.id, title, description, release_year, "
					+ "language.name \"Language\", rental_duration, rental_rate, length, "
					+ "replacement_cost, rating, special_features\n"
					+ "From film JOIN language ON film.language_id = language.id\n" + "WHERE film.id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet filmResult = stmt.executeQuery();
			if (filmResult.next()) {
				int id = filmResult.getInt(1);
				String title = filmResult.getString(2);
				String desc = filmResult.getString(3);
				int releaseYear = filmResult.getInt(4);
				String langId = filmResult.getString(5);
				int rentDur = filmResult.getInt(6);
				double rate = filmResult.getDouble(7);
				int length = filmResult.getInt(8);
				double repCost = filmResult.getDouble(9);
				String rating = filmResult.getString(10);
				String features = filmResult.getString(11);

				Connection conn2 = DriverManager.getConnection(URL, user, pass);
				String sql2 = "SELECT actor.id \"Actor ID\", first_name, last_name\n"
						+ "FROM actor JOIN film_actor ON actor.id = film_actor.actor_id\n"
						+ "JOIN film ON film_actor.film_id = film.id\n" + "WHERE film.id = ?";
				PreparedStatement stmt2 = conn2.prepareStatement(sql2);
				stmt2.setInt(1, filmId);
				ResultSet findActorResult = stmt2.executeQuery();
				while (findActorResult.next()) {
					int actorId = findActorResult.getInt(1);
					String firstName = findActorResult.getString(2);
					String lastName = findActorResult.getString(3);
					Actor actor = new Actor(actorId, firstName, lastName);
					actors.add(actor);

				}
				film = new Film(id, title, desc, releaseYear, langId, rentDur, rate, length, repCost, rating, features,
						actors);
			}
			filmResult.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return film;
	}

	@Override
	public Actor findActorById(int actorId) {
		Actor actor = null;
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, actorId);
			ResultSet actorResult = stmt.executeQuery();
			if (actorResult.next()) {
				actor = new Actor(actorId, sql, sql);
				actor.setId(actorResult.getInt(1));
				actor.setFirstName(actorResult.getString(2));
				actor.setLastName(actorResult.getString(3));
			}
			actorResult.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actor;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		List<Actor> actors = new ArrayList<>();

		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT actor.id \"Actor ID\", first_name, last_name, film.title\n"
					+ "FROM actor JOIN film_actor ON actor.id = film_actor.actor_id\n"
					+ "JOIN film ON film_actor.film_id = film.id\n" + "WHERE film.id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet findActorResult = stmt.executeQuery();
			while (findActorResult.next()) {
				int actorId = findActorResult.getInt(1);
				String firstName = findActorResult.getString(2);
				String lastName = findActorResult.getString(3);
				Actor actor = new Actor(actorId, firstName, lastName);
				actors.add(actor);
			}
			findActorResult.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return actors;
	}

	@Override
	public List<Film> findFilmBySearch(String search) {
		Film film = null;
		List<Film> films = new ArrayList<>();
		int id;
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT film.id, title, description, release_year, language.name \"Language\", rental_duration, rental_rate, length, replacement_cost, rating,\n"
					+ "special_features\n" + "From film JOIN language ON film.language_id = language.id\n"
					+ "WHERE title LIKE ? OR description LIKE ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%" + search + "%");
			stmt.setString(2, "%" + search + "%");
			ResultSet filmResult = stmt.executeQuery();
			while (filmResult.next()) {
				id = filmResult.getInt(1);
				String title = filmResult.getString(2);
				String desc = filmResult.getString(3);
				int releaseYear = filmResult.getInt(4);
				String langId = filmResult.getString(5);
				int rentDur = filmResult.getInt(6);
				double rate = filmResult.getDouble(7);
				int length = filmResult.getInt(8);
				double repCost = filmResult.getDouble(9);
				String rating = filmResult.getString(10);
				String features = filmResult.getString(11);

				Connection conn2 = DriverManager.getConnection(URL, user, pass);
				String sql2 = "SELECT actor.id \"Actor ID\", first_name, last_name\n"
						+ "FROM actor JOIN film_actor ON actor.id = film_actor.actor_id\n"
						+ "JOIN film ON film_actor.film_id = film.id\n" + "WHERE film.id = ?";
				PreparedStatement stmt2 = conn2.prepareStatement(sql2);
				stmt2.setInt(1, id);
				ResultSet findActorResult = stmt2.executeQuery();
				List<Actor> actors = new ArrayList<>();
				while (findActorResult.next()) {
					int actorId = findActorResult.getInt(1);
					String firstName = findActorResult.getString(2);
					String lastName = findActorResult.getString(3);
					Actor actor = new Actor(actorId, firstName, lastName);
					actors.add(actor);

				}

				film = new Film(id, title, desc, releaseYear, langId, rentDur, rate, length, repCost, rating, features,
						actors);

				films.add(film);
			}
			filmResult.close();
			stmt.close();
			conn.close();
		} catch (
		SQLException e) {
			e.printStackTrace();
		}

		return films;
	}

}
