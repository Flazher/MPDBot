package mopidy.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MPDArtistVerbose extends MPDBaseModel {

    public static final MPDArtistVerbose NOT_AVAILABLE = new MPDArtistVerbose();

    static {
        NOT_AVAILABLE.name = "N/A";
    }

    @JsonProperty("name")
    private String name;

    public String getName() {
        return name;
    }
}
