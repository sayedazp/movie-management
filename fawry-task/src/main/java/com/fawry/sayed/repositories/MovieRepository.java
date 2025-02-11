package com.fawry.sayed.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.fawry.sayed.dto.AverageMovieRating;
import com.fawry.sayed.dto.PersistedMovie;
import com.fawry.sayed.dto.ToAddMovie;
import com.fawry.sayed.entities.Movie;

public interface MovieRepository extends PagingAndSortingRepository<Movie, Long>, CrudRepository<Movie,Long>{
	
	<T> Page<T> findAllBy (Pageable pageable, Class<T> type);
	void deleteAllInBatchByIdIn(List<Long> ids);
	
	
	//Will only work if the db supports ilike operators
	
	@Query("SELECT new com.fawry.sayed.dto.PersistedMovie(m.id, m.title, m.releaseYear, m.imdbId, m.type, m.poster)"+
			"m FROM Movie m " +
	        "WHERE m.title ILIKE LOWER(CONCAT('%', :searchTerm, '%'))" +
	        "OR m.type ILIKE LOWER(CONCAT('%', :searchTerm, '%')) ")
    Page<PersistedMovie> searchByTitleorType(Pageable pageable, @Param("searchTerm") String searchTerm);
	
}
