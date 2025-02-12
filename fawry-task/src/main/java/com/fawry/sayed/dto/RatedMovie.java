package com.fawry.sayed.dto;



import com.fawry.sayed.entities.Rating;
import com.fawry.sayed.entities.Type;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RatedMovie {
	
	
	@NotNull(message = "The rate should be provided!")
	@Min(value = 1, message = "The rating should be at least 1")
	@Max(value = 5, message = "The rating should be 5 max")
	private int rating;
	
	
	@NotNull(message = "movie id should be provided!")
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
