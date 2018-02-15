package mopidy.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MPDBaseModel {

    @JsonProperty("__model__")
    protected String model;

    public String getModelName() {
        return model;
    }
}
