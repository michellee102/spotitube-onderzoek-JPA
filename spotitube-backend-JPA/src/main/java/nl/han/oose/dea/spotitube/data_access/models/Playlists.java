package nl.han.oose.dea.spotitube.data_access.models;

import java.util.List;

public class Playlists {
  private List<Playlist> playlists;
  private int length;

  public Playlists(List<Playlist> playlists) {
    this.playlists = playlists;
  }

  public List<Playlist> getPlaylists() {
    return playlists;
  }

  public void setPlaylists(List<Playlist> playlists) {
    this.playlists = playlists;
  }
}
