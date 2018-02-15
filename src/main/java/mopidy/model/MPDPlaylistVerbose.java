package mopidy.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MPDPlaylistVerbose extends MPDBaseModel implements TelegramListItem {

    @JsonProperty("name")
    private String name;

    @JsonProperty("uri")
    private String uri;

    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }

    @Override
    public String getTitle() {
        return name;
    }
}
