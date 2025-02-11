package com.fawry.sayed.dto;

public class AverageMovieRating {
	
	private Long id;
	
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
