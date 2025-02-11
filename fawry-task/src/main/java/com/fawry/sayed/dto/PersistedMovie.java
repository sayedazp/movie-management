package com.fawry.sayed.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fawry.sayed.entities.Movie;
import com.fawry.sayed.entities.Type;

public class PersistedMovie {

	private Long id;

	@JsonProperty("Title")
	private String title;
	
	@JsonProperty("Year")
	private Integer releaseYear;
	
	@JsonProperty("imdbID")
	private String imdbId;
	
	@JsonProperty("Type")
	private Type type;
	
	

	@JsonProperty("Poster")
    private String poster;
 
	public PersistedMovie(Long id, String title, Integer releaseYear, String imdbId, Type type, String poster) {
	this.id = id;
	this.title = title;
	this.releaseYear = releaseYear;
	this.imdbId = imdbId;
	this.type = type;
	this.poster = poster;
}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Integer getYear() {
		return releaseYear;
	}
	public void setYear(Integer year) {
		this.releaseYear = year;
	}
	
	public Integer getReleaseYear() {
		return releaseYear;
	}
	public void setReleaseYear(Integer releaseYear) {
		this.releaseYear = releaseYear;
	}
	
	public String getImdbId() {
		return imdbId;
	}
	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
    
	public Movie createMovie() {
    	Movie m = new Movie();
    	m.setImdbId(imdbId);
    	m.setPoster(poster);
    	m.setReleaseYear(releaseYear);
    	m.setTitle(title);
    	m.setType(type);
    	return m;
    }
}
