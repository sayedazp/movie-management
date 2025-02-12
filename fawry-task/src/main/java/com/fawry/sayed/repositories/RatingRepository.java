package com.fawry.sayed.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.fawry.sayed.dto.AverageMovieRating;
import com.fawry.sayed.dto.RatedMovie;
import com.fawry.sayed.entities.Movie;
import com.fawry.sayed.entities.Rating;
import com.fawry.sayed.entities.User;

public interface RatingRepository extends CrudRepository<Rating, Long>{
	
	
	@Query("SELECT new com.fawry.sayed.dto.AverageMovieRating(r.movie.id, AVG(r.rate)) " +
			"FROM Rating AS r WHERE r.movie.id=:movieId GROUP BY r.movie")
	Optional<AverageMovieRating> avergaMovieRating(@Param("movieId") Long movieId);
	
	void deleteAllInBatchByMovieIdIn(List<Long> ids);
	
	<T> Optional<T> findByUserAndMovie(User user,Movie movie, Class<T> type );
}
