package nl.han.oose.dea.spotitube.data_access.database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class DatabaseSetupTest {
  private final String INSERT_DUMMY_TRACKS_QUERY = "INSERT INTO tracks (title, performer, duration, album, playcount, publicationDate, description, offlineAvailable) VALUES " +
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
  private DatabaseSetup sut;
  private Connection mockedConnection;
  private PreparedStatement mockedStatement;
  private ResultSet mockedResultset;

  @BeforeEach
  public void setup() throws SQLException {


    this.mockedConnection = mock(Connection.class);
    this.sut = new DatabaseSetup(mockedConnection);
    this.mockedStatement = mock(PreparedStatement.class);
    this.mockedResultset = mock(ResultSet.class);

    when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedStatement);
    when(mockedConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockedStatement);
  }

  @Test
  public void createUsersTable_ExecutesCorrectly() throws SQLException {
    sut.createUsersTable();

    verify(mockedConnection, times(1)).prepareStatement("CREATE TABLE users (username VARCHAR(255) NOT NULL PRIMARY KEY, password VARCHAR(255) NOT NULL, token VARCHAR(255) NOT NULL)");
    verify(mockedStatement, times(1)).execute();
  }

  @Test
  public void createUsersTable_ThrowsRuntimeException() throws SQLException {
    when(mockedStatement.execute()).thenThrow(new RuntimeException());
    assertThrows(RuntimeException.class, () -> this.sut.createUsersTable());
  }

  @Test
  public void createPlaylistsTable_ExecutesCorrectly() throws SQLException {
    sut.createPlaylistsTable();

    verify(mockedConnection, times(1)).prepareStatement("CREATE TABLE playlists (id INT NOT NULL AUTO_INCREMENT, name VARCHAR(255) NOT NULL, owner TINYINT(1) NOT NULL, username VARCHAR(255) NOT NULL, PRIMARY KEY (id), FOREIGN KEY (username) REFERENCES users(username))");
    verify(mockedStatement, times(1)).execute();
  }

  @Test
  public void createTracksTable_ExecutesCorrectly() throws SQLException {
    sut.createTracksTable();

    verify(mockedConnection, times(1)).prepareStatement("CREATE TABLE tracks (id INT NOT NULL AUTO_INCREMENT, title VARCHAR(255) NOT NULL, performer VARCHAR(255) NOT NULL , duration SMALLINT UNSIGNED NOT NULL, album VARCHAR(255), playcount INT UNSIGNED, publicationDate VARCHAR(255), description VARCHAR(255), offlineAvailable BOOLEAN, playlist_id INT, PRIMARY KEY (id), FOREIGN KEY (playlist_id) REFERENCES playlists(id) ON DELETE SET NULL )");
    verify(mockedStatement, times(1)).execute();
  }

  @Test
  public void addDummyData_ExecutesCorrectly() throws SQLException {
    sut.addDummyDataForTracks();

    verify(mockedConnection, times(1)).prepareStatement(INSERT_DUMMY_TRACKS_QUERY);
    verify(mockedStatement, times(1)).executeUpdate();
  }

  @Test
  public void testSetupSpotitubeDatabase() throws SQLException {
    sut.setupSpotitubeDatabase();

    verify(mockedConnection, times(4)).prepareStatement(anyString());
//    verify(mocked, times(1)).connect();
  }

}
