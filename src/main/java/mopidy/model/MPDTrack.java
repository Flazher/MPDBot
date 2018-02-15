package mopidy.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MPDTrack extends MPDBaseModel {

    @JsonProperty("name")
    private String name;

    @JsonProperty("artists")
    private List<MPDArtistVerbose> artists;

    public String getName() {
        return name;
    }

    public List<MPDArtistVerbose> getArtists() {
        return artists;
    }

    public MPDArtistVerbose getSingleArtist() {
        return artists.size() != 0 ? artists.get(0) : MPDArtistVerbose.NOT_AVAILABLE;
    }
}
