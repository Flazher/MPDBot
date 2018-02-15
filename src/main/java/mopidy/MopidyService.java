package mopidy;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import mopidy.model.request.MPDBaseRequest;
import mopidy.model.response.*;

public class MopidyService {

    private String mopidyApiUrl;
    private WebClient client;

    public static MopidyService create(Vertx vertx, JsonObject config) {
        MopidyService mopidyService = new MopidyService();
        mopidyService.mopidyApiUrl = config.getString("mpd.api.uri");

        WebClientOptions options = new WebClientOptions()
                .setUserAgent("MPDBot/1.0.0");

        mopidyService.client = WebClient.create(vertx);
        return mopidyService;
    }

    private MopidyService() {
    }

    public Future<MPDGetPlaylistsResponse> getPlaylists() {
        Future<MPDGetPlaylistsResponse> future = Future.future();
        execute("core.playlists.get_playlists", new JsonObject().put("include_tracks", false), result -> {
            if (result.succeeded())
                future.complete(result.result().bodyAsJson(MPDGetPlaylistsResponse.class));
            else
                future.fail("");
        });
        return future;
    }

    public Future<MPDGetTrackResponse> getCurrentTrack() {
        Future<MPDGetTrackResponse> future = Future.future();
        execute("core.playback.get_current_track", new JsonObject(), result -> {
            if (result.succeeded())
                future.complete(result.result().bodyAsJson(MPDGetTrackResponse.class));
            else
                future.fail("");
        });
        return future;
    }

    public Future<MPDGetMuteResponse> getMute() {
        Future<MPDGetMuteResponse> future = Future.future();
        execute("core.mixer.get_mute", new JsonObject(), result -> {
            if (result.succeeded())
                future.complete(result.result().bodyAsJson(MPDGetMuteResponse.class));
            else
                future.fail("");
        });
        return future;
    }

    public Future<Boolean> mute() {
        return getMute().compose(currentState -> {
            Future<Boolean> future = Future.future();
            execute("core.mixer.set_mute", new JsonObject().put("mute", !currentState.getResult()), result -> {
                if (result.succeeded())
                    future.complete(!currentState.getResult());
                else
                    future.complete(false);
            });
            return future;
        });
    }

    public Future<MPDGetVolumeResponse> getVolume() {
        Future<MPDGetVolumeResponse> future = Future.future();
        execute("core.mixer.get_volume", new JsonObject(), result -> {
            if (result.succeeded())
                future.complete(result.result().bodyAsJson(MPDGetVolumeResponse.class));
            else
                future.fail("");
        });
        return future;
    }

    public Future<MPDBaseResponse<Void>> setVolume(Integer volume) {
        Future<MPDBaseResponse<Void>> future = Future.future();
        execute("core.mixer.set_volume", new JsonObject().put("volume", volume), result -> {
            if (result.succeeded())
                future.complete(result.result().bodyAsJson(MPDBaseResponse.class));
            else
                future.fail("");
        });
        return future;
    }

    public Future<Void> pause() {
        Future<Void> future = Future.future();
        execute("core.playback.pause", new JsonObject(), result -> {
            if (result.succeeded())
                future.complete();
            else
                future.fail("");
        });
        return future;
    }

    public Future<Void> play() {
        Future<Void> future = Future.future();
        execute("core.playback.play", new JsonObject(), result -> {
            if (result.succeeded())
                future.complete();
            else
                future.fail("");
        });
        return future;
    }

    public Future<Void> prev() {
        Future<Void> future = Future.future();
        execute("core.playback.previous", new JsonObject(), result -> {
            if (result.succeeded())
                future.complete();
            else
                future.fail("");
        });
        return future;
    }

    public Future<Void> next() {
        Future<Void> future = Future.future();
        execute("core.playback.next", new JsonObject(), result -> {
            if (result.succeeded())
                future.complete();
            else
                future.fail("");
        });
        return future;
    }

/*    public Future<MPDGetCurrentTlTrackResponse> getCurrentTlTrack() {
        Future<MPDGetCurrentTlTrackResponse> future = Future.future();
        execute("core.playback.get_current_tl_track", new JsonObject(), result -> {
            if (result.succeeded())
                future.complete(result.result().bodyAsJson(MPDGetCurrentTlTrackResponse.class));
            else
                future.fail("");
        });
        return future;
    }*/

    private void execute(String method, JsonObject params, Handler<AsyncResult<HttpResponse<Buffer>>> handler) {
        MPDBaseRequest request = MPDBaseRequest.blank()
                .method(method)
                .params(params);

        client.postAbs(mopidyApiUrl).sendJson(request, handler);
    }
}
