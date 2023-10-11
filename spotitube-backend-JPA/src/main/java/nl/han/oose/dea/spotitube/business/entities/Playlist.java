package nl.han.oose.dea.spotitube.business.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "playlists")
public class Playlist {

  @Id
  @GeneratedValue
  private int id;
  private String name;
  private boolean owner;
  private String username;

  public Playlist() {
  }

  public Playlist(int id, String name, boolean owner, String username) {
    this.id = id;
    this.name = name;
    this.owner = owner;
    this.username = username;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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
}
