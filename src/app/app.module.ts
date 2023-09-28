import 'hammerjs';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {SpotitubeAngularModule} from './modules/angular.module';
import {SpotitubeMaterialModule} from './modules/material.module';
import {LoginComponent} from './components/login/login.component';
import {LoginService} from './services/login/login.service';
import {LoggingService} from './services/logging/logging.service';
import {PlaylistOverviewComponent} from './components/playlists-overview/playlists-overview.component';
import {PlaylistComponent} from './components/playlist/playlist.component';
import {PlaylistsComponent} from './components/playlists/playlists.component';
import {PlaylistService} from './services/playlist/playlist.service';
import {MinutesPipe} from './pipes/minutes.pipe';
import {EditPlaylistDialogComponent} from './dialogs/edit-playlist.dialog/edit-playlist.dialog.component';
import {NewPlaylistDialogComponent} from './dialogs/new-playlist.dialog/new-playlist.dialog.component';
import {TrackService} from './services/track/track.service';
import {AddTrackDialogComponent} from './dialogs/add-track.dialog/add-track.dialog.component';

@NgModule({
  declarations: [
    AddTrackDialogComponent,
    AppComponent,
    EditPlaylistDialogComponent,
    LoginComponent,
    MinutesPipe,
    NewPlaylistDialogComponent,
    PlaylistOverviewComponent,
    PlaylistComponent,
    PlaylistsComponent
  ],
  entryComponents: [
    AddTrackDialogComponent,
    EditPlaylistDialogComponent,
    NewPlaylistDialogComponent
  ],
  imports: [
    SpotitubeAngularModule,
    SpotitubeMaterialModule
  ],
  providers: [
    LoggingService,
    LoginService,
    PlaylistService,
    TrackService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
