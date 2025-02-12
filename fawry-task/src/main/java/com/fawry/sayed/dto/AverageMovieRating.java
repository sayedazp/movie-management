package com.fawry.sayed.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AverageMovieRating {
	
	
	@NotNull(message = "Movie id should be provided")
	private Long id;
	
	
	@Min(value = 0, message = "The rating should be at least 1")
	@Max(value = 5, message = "The rating should be 5 max")
	private Double avgRating;
	
	public AverageMovieRating(Long id, Double avgRating) {
		this.id = id;
		this.avgRating = avgRating;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getAvgRating() {
		return avgRating;
	}
	public void setAvgRating(Double avgRating) {
		this.avgRating = avgRating;
	}
	
	

}
