package nl.han.oose.dea.spotitube.data_access.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tracks")
public class Track {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String title;
  private String performer;
  private int duration;
  private String album;
  private int playCount;
  private String publicationDate;
  private String description;
  private boolean offlineAvailable;

  @ManyToOne()
  private Playlist playlist;

  public Track(int id, String title, String performer, int duration, String album, int playCount, String publicationDate, String description, boolean offlineAvailable) {
    this.id = id;
    this.title = title;
    this.performer = performer;
    this.duration = duration;
    this.album = album;
    this.playCount = playCount;
    this.publicationDate = publicationDate;
    this.description = description;
    this.offlineAvailable = offlineAvailable;
  }

  public Track(int id, String title, String performer, int duration) {
    this.id = id;
    this.title = title;
    this.performer = performer;
    this.duration = duration;
  }

  public Track() {

  }
  // Getters and setters

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getPerformer() {
    return performer;
  }

  public void setPerformer(String performer) {
    this.performer = performer;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public String getAlbum() {
    return album;
  }

  public void setAlbum(String album) {
    this.album = album;
  }

  public int getPlayCount() {
    return playCount;
  }

  public void setPlayCount(int playCount) {
    this.playCount = playCount;
  }

  public String getPublicationDate() {
    return publicationDate;
  }

  public void setPublicationDate(String publicationDate) {
    this.publicationDate = publicationDate;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isOfflineAvailable() {
    return offlineAvailable;
  }

  public void setOfflineAvailable(boolean offlineAvailable) {
    this.offlineAvailable = offlineAvailable;
  }

  public Playlist getPlaylist() {
    return playlist;
  }

  public void setPlaylist(Playlist playlist) {
    this.playlist = playlist;
  }
}
