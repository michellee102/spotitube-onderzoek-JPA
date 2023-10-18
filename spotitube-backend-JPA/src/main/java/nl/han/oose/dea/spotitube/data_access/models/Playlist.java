package nl.han.oose.dea.spotitube.data_access.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "playlists")
public class Playlist {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String name;
  private boolean owner;
  private String username;

  @OneToMany(mappedBy = "playlist")
  private List<Track> tracks;

  public Playlist(int id, String name, boolean owner, String username, List<Track> tracks) {
    this.id = id;
    this.name = name;
    this.owner = owner;
    this.username = username;
    this.tracks = tracks;
  }

  public Playlist(String name, boolean owner, String username){
    this.name = name;
    this.owner = owner;
    this.username = username;
  }

  public Playlist() {
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
