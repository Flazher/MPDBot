package telegram;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import mopidy.MopidyService;
import mopidy.model.MPDPlaylistVerbose;
import mopidy.model.response.MPDGetTrackResponse;
import mopidy.model.response.MPDGetVolumeResponse;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;

public class MPDBotContext {

    private Vertx vertx;
    private JsonObject config;
    private MopidyService mopidyService;

    private Integer playlistOffset = 0;
    private List<MPDPlaylistVerbose> playlists = new ArrayList<>();

    public static MPDBotContext init(Vertx vertx, JsonObject config) {
        return new MPDBotContext(vertx, config);
    }

    private MPDBotContext(Vertx vertx, JsonObject config) {
        this.mopidyService = MopidyService.create(vertx, config);
    }

    public void redrawControlPanel(Future<Void> future, SendMessage sendMessage, String msg) {
        CompositeFuture.all(
                mopidyService.getCurrentTrack(),
                mopidyService.getVolume()
        ).setHandler(rs -> {
            MPDGetTrackResponse track = rs.result().resultAt(0);
            MPDGetVolumeResponse volume = rs.result().resultAt(1);

            sendMessage.setText(msg);
            MPDTelegramUtils.sendControlPanel(track.getResult(), volume.getResult(), sendMessage);
            future.complete();
        });
    }

    public Vertx getVertx() {
        return vertx;
    }

    public JsonObject getConfig() {
        return config;
    }

    public MopidyService getMopidyService() {
        return mopidyService;
    }

    public Integer getPlaylistOffset() {
        return playlistOffset;
    }

    public List<MPDPlaylistVerbose> getPlaylists() {
        return playlists;
    }

    public void setPlaylistOffset(Integer playlistOffset) {
        this.playlistOffset = playlistOffset;
    }

    public void setPlaylists(List<MPDPlaylistVerbose> playlists) {
        this.playlists = playlists;
    }
}
