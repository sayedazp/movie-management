package com.fawry.sayed.dto;

import com.fawry.sayed.entities.Rating;
import com.fawry.sayed.entities.Type;

public class RatedMovie {
	
	private int rating;
	
	private Long movieId;
	
	

	public RatedMovie() {
	}

	public RatedMovie(int rating, Long movieId) {
		this.rating = rating;
		this.movieId = movieId;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Long getMovieId() {
		return movieId;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}
}
