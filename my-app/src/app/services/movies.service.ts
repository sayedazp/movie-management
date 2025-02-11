import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AverageMovieRate, MovieDetailed, MoviesSearchReturn, OMBDMovie, RatedMovie, SearchFilters } from '../../types';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MoviesService {

  private apiUrl = 'https://www.omdbapi.com/';
  private apiKey = 'f1a699c2';
  private localServer = "http://localhost:8080/"
  constructor(private httpClient:HttpClient) { 
  }

  getAllMoviesPaginated(page: number, pageSize: number):Observable<any>{
    const params = new HttpParams()
    .set('page', page.toString())
    .set('pageSize', pageSize.toString());
    return this.httpClient.get(this.localServer.concat('movies/all'), { params, withCredentials: true});
  }
  searchAllMoviesPaginated(page: number, pageSize: number, searchTerm:String):Observable<any>{
    const params = new HttpParams()
    .set('page', page.toString())
    .set('pageSize', pageSize.toString())
    .set('searchTerm', searchTerm.toString())
    return this.httpClient.get(this.localServer.concat('movies/search'), { params, withCredentials: true });
  }

  searchMoviesOMDB(filters:SearchFilters):Observable<MoviesSearchReturn>{
    const params = new HttpParams()
      .set('apikey', this.apiKey)
      .set('s', filters.s.toString())         
      .set('y', filters.y.toString()  )          
      .set('type', filters.type);
    return this.httpClient.get<any>(this.apiUrl, {params}, );
  }

  getmMovieDetailsOMDB(id:String):Observable<MovieDetailed>{
    const params = new HttpParams()
      .set('apikey', this.apiKey)
      .set('i', id.toString())         
    return this.httpClient.get<any>(this.apiUrl, {params}, );
  }

  addMovie(movie:OMBDMovie):Observable<object>{
    return this.httpClient.post<any>(this.localServer.concat('movies/add'), movie, {withCredentials: true});
  }

  addMovies(movies:OMBDMovie[]):Observable<object>{
    return this.httpClient.post<any>(this.localServer.concat('movies/add/all'), movies, {withCredentials: true});
  }

  deleteMovie(id:Number):Observable<object>{
    return this.httpClient.delete<any>(this.localServer.concat(`movies/delete/${id}`), {withCredentials: true});
  }

  deleteMovies(ids:Number[]):Observable<object>{
    return this.httpClient.post<any>(this.localServer.concat(`movies/delete/all`), ids, {withCredentials: true});
  }

  rateMovie(ratedMovie:RatedMovie):Observable<RatedMovie>{
    return this.httpClient.post<any>(this.localServer.concat(`movies/rate`), ratedMovie, {withCredentials: true});
  }

  getUserMovieRate(id:Number):Observable<RatedMovie>{
    return this.httpClient.get<any>(this.localServer.concat(`movies/rate/${id}`), {withCredentials: true});
  }

  getAvgMovieRate(id:Number):Observable<AverageMovieRate>{
    return this.httpClient.get<any>(this.localServer.concat(`movies/rate/avg/${id}`), {withCredentials: true});
  }

  // private handleError(error: HttpErrorResponse) {
  //   let errorMessage = 'An unknown error occurred!';
  //   if (error.error instanceof ErrorEvent) {
  //     // Client-side or network error
  //     errorMessage = `Error: ${error.error.message}`;
  //   } else {
  //     // Backend error (e.g., 404 or 500)
  //     errorMessage = `Server returned code: ${error.status}, error message is: ${error.message}`;
  //   }
  //   return throwError(errorMessage);  // Return an observable with the error message
  // }
}


