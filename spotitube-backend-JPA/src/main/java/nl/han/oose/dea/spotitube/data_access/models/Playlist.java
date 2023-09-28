package nl.han.oose.dea.spotitube.data_access.models;

import java.util.List;

public class Playlist {
  private final int id;
  private String name;
  private boolean owner;
  private String username;

  private List<Track> tracks;

  public Playlist(int id, String name, boolean owner, String username, List<Track> tracks) {
    this.id = id;
    this.name = name;
    this.owner = owner;
    this.username = username;
    this.tracks = tracks;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isOwner() {
    return owner;
  }

  public void setOwner(boolean owner) {
    this.owner = owner;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public List<Track> getTracks() {
    return tracks;
  }

  public void setTracks(List<Track> tracks) {
    this.tracks = tracks;
  }

}
