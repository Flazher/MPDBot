package mopidy.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MPDBaseResponse<T> {

    @JsonProperty("jsonrpc")
    protected String jsonRpc;

    @JsonProperty("id")
    protected Integer id;

    @JsonProperty("error")
    protected String error;

    @JsonProperty("result")
    protected T result;

    public String getJsonRpc() {
        return jsonRpc;
    }

    public Integer getId() {
        return id;
    }

    public T getResult() {
        return result;
    }

    public boolean ok() {
        return error == null;
    }
}
