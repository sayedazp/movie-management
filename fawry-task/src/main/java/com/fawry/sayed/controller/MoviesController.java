package com.fawry.sayed.controller;

import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fawry.sayed.dto.AverageMovieRating;
import com.fawry.sayed.dto.PaginatedResponse;
import com.fawry.sayed.dto.PersistedMovie;
import com.fawry.sayed.dto.RatedMovie;
import com.fawry.sayed.dto.ToAddMovie;
import com.fawry.sayed.entities.Movie;
import com.fawry.sayed.repositories.MovieRepository;
import com.fawry.sayed.services.MoviesServices;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("movies")
@CrossOrigin(origins = "http://localhost:4200")
public class MoviesController {
	
	@Autowired
	MovieRepository movieRepository;
	
	private final MoviesServices moviesServices;	

	public MoviesController(MoviesServices moviesServices) {
		this.moviesServices = moviesServices;
	}

	@GetMapping("all")
	PaginatedResponse<PersistedMovie> allMovies(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
		return moviesServices.getMovies(page, size);
	}
	@PostMapping("add")
	ResponseEntity<Void> addMovie(@RequestBody @Valid ToAddMovie movie){
		moviesServices.addMovie(movie);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}	
	@PostMapping("add/all")
	ResponseEntity<Void> addMovies(@RequestBody @Valid List<ToAddMovie> movies){
		moviesServices.addMovies(movies);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@DeleteMapping("delete/{id}")
	ResponseEntity<Void> deleteMovie(@PathVariable(name = "id", required = true) Long id){
		moviesServices.deleteMovie(id);
		return ResponseEntity.noContent().build();
	}
	
	//delete mapping
	@PostMapping("delete/all")
	ResponseEntity<Void> deleteAllSelectedMovies(@RequestBody @NotBlank(message = "movies ids should provided")
	List<Long> ids){
		moviesServices.deleteAllSelected(ids);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("rate")
	ResponseEntity<RatedMovie> rateMovie(@RequestBody @Valid RatedMovie ratedMovie){
		RatedMovie rm = moviesServices.rateMovie(ratedMovie);
		return ResponseEntity.ok(rm);
	}
	
	@GetMapping("rate/avg/{id}")
	ResponseEntity<AverageMovieRating> getAvgMovieRate(@PathVariable(name = "id") Long id){
		AverageMovieRating avgMovieRate = moviesServices.getMovieRate(id);
		return ResponseEntity.ok(avgMovieRate);
	}

	@GetMapping("rate/{id}")
	ResponseEntity<RatedMovie> getMoveRate(@PathVariable(name = "id") Long id) {
		RatedMovie ratedMovie = moviesServices.getMovieRateUser(id);
		return ResponseEntity.ok(ratedMovie);
	}

	@GetMapping("search")
	PaginatedResponse<PersistedMovie> searchMovie(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(value = "searchTerm") String searchTerm){
		return moviesServices.searchMovies(page, size, searchTerm);
	}
}
