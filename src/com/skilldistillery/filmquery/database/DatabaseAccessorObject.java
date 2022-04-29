package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=US/Mountain";

	public DatabaseAccessorObject() throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
	}
	@Override
	public Film findFilmById(int filmId) throws SQLException {
//		Film film = null;
		String user = "student";
		String pass = "student";
		Connection conn = DriverManager.getConnection(URL, user, pass);
		String sql = "SELECT id, title, description, release_year, language_id, rental_duration, rental_rate, length, replacement_cost, rating, special_features FROM film WHERE id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, filmId);
		ResultSet filmResult = stmt.executeQuery();
		if(filmResult.next()) {
			Film film = new Film(filmId, sql, sql, filmId, filmId, filmId, filmId, filmId, filmId, sql, sql);
		}
		
		while (filmResult.next()) {
		      filmId = filmResult.getInt(1);
		      String title = filmResult.getString(2);
		      String desc = filmResult.getString(3);
		      short releaseYear = filmResult.getShort(4);
		      int langId = filmResult.getInt(5);
		      int rentDur = filmResult.getInt(6);
		      double rate = filmResult.getDouble(7);
		      int length = filmResult.getInt(8);
		      double repCost = filmResult.getDouble(9);
		      String rating = filmResult.getString(10);
		      String features = filmResult.getString(11);
		      Film film = new Film(filmId, title, desc, releaseYear, langId,
		                           rentDur, rate, length, repCost, rating, features);
		      film.add(film);
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
	public Actor findActorById(int actorId) throws SQLException {
		Actor actor = null;
		// ...
		String user = "student";
		String pass = "student";
	  Connection conn = DriverManager.getConnection(URL, user, pass);
		String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, actorId);
		ResultSet actorResult = stmt.executeQuery();
		if (actorResult.next()) {
			actor = new Actor(actorId, sql, sql); // Create the object
			// Here is our mapping of query columns to our object fields:
			actor.setId(actorResult.getInt(1));
			actor.setFirstName(actorResult.getString(2));
			actor.setLastName(actorResult.getString(3));
//	    actor.setFilms(findFilmById(actorId)); // An Actor has Films
		}
		// ...
		return actor;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {

		return null;
	}

}
