package mopidy.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.vertx.core.json.JsonObject;

public class MPDBaseRequest {

    @JsonProperty("jsonrpc")
    protected String jsonRpc;

    @JsonProperty("id")
    protected Integer id;

    @JsonProperty("method")
    protected String _method;

    @JsonProperty("params")
    protected JsonObject _params;

    public static MPDBaseRequest blank() {
        MPDBaseRequest request = new MPDBaseRequest();
        request.id = 1;
        request.jsonRpc = "2.0";
        return request;
    }

    public MPDBaseRequest method(String method) {
        this._method = method;
        return this;
    }

    public MPDBaseRequest params(JsonObject params) {
        this._params = params;
        return this;
    }
}
