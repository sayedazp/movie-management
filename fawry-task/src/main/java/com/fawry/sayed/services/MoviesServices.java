package com.fawry.sayed.services;

//import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fawry.sayed.dto.AverageMovieRating;
import com.fawry.sayed.dto.PaginatedResponse;
import com.fawry.sayed.dto.PersistedMovie;
import com.fawry.sayed.dto.RatedMovie;
import com.fawry.sayed.dto.ToAddMovie;
import com.fawry.sayed.entities.Movie;
import com.fawry.sayed.entities.Rating;
import com.fawry.sayed.entities.User;
import com.fawry.sayed.repositories.MovieRepository;
import com.fawry.sayed.repositories.RatingQueries;
import com.fawry.sayed.repositories.RatingRepository;
import com.fawry.sayed.repositories.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class MoviesServices {
	
	private final MovieRepository movieRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RatingRepository ratingRepository;
	
	
	@PersistenceContext
    private EntityManager entityManager;

	public MoviesServices(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}
	
	
	public void addMovie(ToAddMovie movie) {
		Movie m = movie.createMovie();
		movieRepository.save(m);
	}
	
	public void addMovies(List<ToAddMovie> movies) {
		List<Movie> new_movies = movies.stream().map((m)->m.createMovie()).collect(Collectors.toList());
		movieRepository.saveAll(new_movies);
	}
	public PaginatedResponse<PersistedMovie> getMovies(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PersistedMovie> moviePage = movieRepository.findAllBy(pageable, PersistedMovie.class);
        PaginatedResponse<PersistedMovie> response = new PaginatedResponse<>();
        response.setContent(moviePage.getContent());
        response.setPageNumber(moviePage.getNumber());
        response.setPageSize(moviePage.getSize());
        response.setTotalElements(moviePage.getTotalElements());
        response.setTotalPages(moviePage.getTotalPages());
        return response;
        }
	public void deleteMovie(Long id) {
		movieRepository.deleteById(id);
	}

	@Transactional	
	public void deleteAllSelected(List<Long> ids) {
		entityManager.flush();
		movieRepository.deleteAllInBatchByIdIn(ids);
	}
	
	public RatedMovie rateMovie(RatedMovie ratedMovie) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth == null) {
			throw new RuntimeException("Authentication needed");
		}
		Movie movie = movieRepository.findById(ratedMovie.getMovieId())
				.orElseThrow(()->
				new EntityNotFoundException("No movie with the specified id")); 
		User user = userRepository.findByEmail(auth.getName()).orElseThrow(()->new EntityNotFoundException());
		Rating rating = ratingRepository.findByUserAndMovie(user, movie, Rating.class).orElse(new Rating());
		rating.setRate(ratedMovie.getRating());
		rating.setUser(user);
		rating.setMovie(movie);
		Rating final_rating = ratingRepository.save(rating);
		return new RatedMovie(final_rating.getRate(), final_rating.getMovie().getId());
	}
	
	public AverageMovieRating getMovieRate(Long id) {
		return ratingRepository.avergaMovieRating(id).orElseThrow(()->new EntityNotFoundException("Not found rating"));
	}
	
	public RatedMovie getMovieRateUser(Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth == null) {
			throw new RuntimeException("Authentication needed");
		}
		User user = userRepository.findByEmail(auth.getName()).orElseThrow(()->new EntityNotFoundException());
		Movie movie = movieRepository.findById(id)
				.orElseThrow(()->
				new EntityNotFoundException("No movie with the specified id")); 
		return ratingRepository.findByUserAndMovie(user, movie, RatedMovie.class).orElseThrow(()->new EntityNotFoundException("Not found rating"));
	}
	
	public PaginatedResponse<PersistedMovie> searchMovies(int page, int size, String searchTerm){
		Pageable pageable = PageRequest.of(page, size);
		Page<PersistedMovie> moviePage = movieRepository.searchByTitleorType(pageable, searchTerm);
        PaginatedResponse<PersistedMovie> response = new PaginatedResponse<>();
        response.setContent(moviePage.getContent());
        response.setPageNumber(moviePage.getNumber());
        response.setPageSize(moviePage.getSize());
        response.setTotalElements(moviePage.getTotalElements());
        response.setTotalPages(moviePage.getTotalPages());
        return response;
	}
}


