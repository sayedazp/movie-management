package com.fawry.sayed.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ratings", uniqueConstraints={
	    @UniqueConstraint(columnNames = {"user_id", "movie_id"}, name = "user_movie_unique")
	})
public class Rating {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@NotNull(message = "The rate should be provided!")
	@Min(value = 1, message = "The rating should be at least 1")
	@Max(value = 5, message = "The rating should be 5 max")
	private int rate;
	
	
	@NotNull(message="The rating user should be provided")
	@ManyToOne()
	@JoinColumn(name="user_id")
	private User user;
	
	
	@NotNull(message="The rated movie should be provided")
	@ManyToOne()
	@JoinColumn(name="movie_id")
	private Movie movie;


	public Movie getMovie() {
		return movie;
	}


	public void setMovie(Movie movie) {
		this.movie = movie;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public int getRate() {
		return rate;
	}


	public void setRate(int rate) {
		this.rate = rate;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
}
