package nl.han.oose.dea.spotitube.data_access.database;

import nl.han.oose.dea.spotitube.data_access.util.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseSetup {
  private final Connection CONNECTION;

  public DatabaseSetup(DatabaseManager dbManager) {
    CONNECTION = dbManager.connect();
  }

  public DatabaseSetup(Connection CONNECTION) {
    this.CONNECTION = CONNECTION;
  }

  public void setupSpotitubeDatabase(){
      createUsersTable();
      createPlaylistsTable();
      createTracksTable();
      addDummyDataForTracks();
  }

  public void createUsersTable(){
    try {
      String CREATE_USERS_TABLE_QUERY = "CREATE TABLE users (username VARCHAR(255) NOT NULL PRIMARY KEY, password VARCHAR(255) NOT NULL, token VARCHAR(255) NOT NULL)";
      PreparedStatement preparedStatement = CONNECTION.prepareStatement(CREATE_USERS_TABLE_QUERY);
      preparedStatement.execute();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
  public void createTracksTable(){
    try {
      String CREATE_TRACKS_TABLE_QUERY = "CREATE TABLE tracks (id INT NOT NULL AUTO_INCREMENT, title VARCHAR(255) NOT NULL, performer VARCHAR(255) NOT NULL , duration SMALLINT UNSIGNED NOT NULL, album VARCHAR(255), playcount INT UNSIGNED, publicationDate VARCHAR(255), description VARCHAR(255), offlineAvailable BOOLEAN, playlist_id INT, PRIMARY KEY (id), FOREIGN KEY (playlist_id) REFERENCES playlists(id) ON DELETE SET NULL )";
      PreparedStatement preparedStatement = CONNECTION.prepareStatement(CREATE_TRACKS_TABLE_QUERY);
      preparedStatement.execute();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public void createPlaylistsTable(){
    try {
      String CREATE_PLAYLISTS_TABLE_QUERY = "CREATE TABLE playlists (id INT NOT NULL AUTO_INCREMENT, name VARCHAR(255) NOT NULL, owner TINYINT(1) NOT NULL, username VARCHAR(255) NOT NULL, PRIMARY KEY (id), FOREIGN KEY (username) REFERENCES users(username))";
      PreparedStatement preparedStatement = CONNECTION.prepareStatement(CREATE_PLAYLISTS_TABLE_QUERY);
      preparedStatement.execute();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public void addDummyDataForTracks(){
    try {
      String INSERT_DUMMY_TRACKS_QUERY = "INSERT INTO tracks (title, performer, duration, album, playcount, publicationDate, description, offlineAvailable) VALUES " +
        "('Shape of You', 'Ed Sheeran', 233, '÷', 5832946, '2017-01-06', 'Hit single from Ed Sheeran', true), " +
        "('Uptown Funk', 'Mark Ronson ft. Bruno Mars', 271, 'Uptown Special', 3739287, '2014-11-10', null, true), " +
        "('Stairway to Heaven', 'Led Zeppelin', 482, 'Led Zeppelin IV', 1990474, null, null, false), " +
        "('Billie Jean', 'Michael Jackson', 293, 'Thriller', 2447745, '1983-01-02', 'One of the most famous pop songs of all time', true), " +
        "('Rolling in the Deep', 'Adele', 228, '21', 2597143, '2010-11-29', 'Adele''s smash hit song', false), " +
        "('Smells Like Teen Spirit', 'Nirvana', 301, 'Nevermind', 3047276, null, null, false), " +
        "('Bohemian Rhapsody', 'Queen', 354, 'A Night at the Opera', 3298202, '1975-10-31', 'Queen classic', true), " +
        "('Hey Jude', 'The Beatles', 431, 'The Beatles Again', 2161804, '1968-08-26', null, true), " +
        "('Hotel California', 'Eagles', 391, 'Hotel California', 2737825, null, null, true), " +
        "('Wonderwall', 'Oasis', 258, '(What''s the Story) Morning Glory?', 2838645, '1995-10-30', null, true)";
      PreparedStatement preparedStatement = CONNECTION.prepareStatement(INSERT_DUMMY_TRACKS_QUERY);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }


}
