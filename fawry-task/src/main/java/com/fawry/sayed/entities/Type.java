package com.fawry.sayed.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Type {

	
	@JsonProperty("movie")
	MOVIE("movie"),
	@JsonProperty("series")
	SERIES("series"),
	@JsonProperty("episode")
	EPISODE("episode"),
	@JsonProperty("game")
	GAME("game");
	
	private final String text;

	Type(String text) {
		this.text = text;
	}

    @JsonValue
	public String toString() {
        return text;
    }
}
