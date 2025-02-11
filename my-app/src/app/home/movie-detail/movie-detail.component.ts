import { Component, Input, OnInit } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import {MatSlider, MatSliderModule} from '@angular/material/slider';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MoviesService } from '../../services/movies.service';
import { MovieDetailed, RatedMovie } from '../../../types';
import { FormsModule } from '@angular/forms';
import { RatingComponent } from "./rating/rating.component";

@Component({
  selector: 'app-movie-detail',
  standalone: true,
  imports: [
    // BrowserModule,
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatDividerModule,
    MatProgressSpinnerModule,
    FormsModule,
    RatingComponent
],
  templateUrl: './movie-detail.component.html',
  styleUrl: './movie-detail.component.scss'
})
export class MovieDetailComponent implements OnInit{
  constructor(private route: ActivatedRoute, private router: Router, private moviesService:MoviesService){}
  userRating:number=0;
  avgRating:number=0;
  OMDBId:String = '';
  dbId:Number = new Number();
  isLoading:Boolean = true;
  err:string  | null = null;
  movie: MovieDetailed = {
    Title: "",
    Year: "",
    Rated: "",
    Released: "",
    Runtime: "",
    Genre: "",
    Director: "",
    Writer: "",
    Actors: "",
    Plot: "",
    Language: "",
    Country: "",
    Awards: "",
    Poster: "",
    Ratings: [],
    Metascore: "",
    imdbRating: "",
    imdbVotes: "",
    imdbID: "",
    BoxOffice: "",
    Website: "",
    Response: ""
  };
  onRatingUpdated(updatedRating: number) {
    let to_rate:RatedMovie = {
        movieId:this.dbId,
        rating:updatedRating
    }
    this.moviesService.rateMovie(to_rate).subscribe(
      (ratedMovie)=>{
        this.userRating = ratedMovie.rating.valueOf()
        this.populateAvg()
      }
    )
    
    console.log('Updated rating from child:', this.userRating);
  }
  populateAvg(){
    this.moviesService.getAvgMovieRate(this.dbId).subscribe(
      (ratedMovie)=> {
        this.avgRating = ratedMovie.avgRating.valueOf()
      }
    )
  }
  validate_input(){
    console.log(this.dbId)
    console.log(this.OMDBId)
    if (this.dbId === null || this.OMDBId == ''){
        console.log("not valid input")
        this.router.navigate(['home'])
    }
  }
  populteMovie(){
    this.moviesService.getmMovieDetailsOMDB(this.OMDBId).subscribe(
      (m)=>{
        console.log(m)
        this.movie = m;
        this.isLoading = false;
      }
    )
  }
  ngOnInit(): void {
    console.log(this.isLoading)
    this.dbId = Number(this.route.snapshot.paramMap.get('id'));
    this.route.queryParamMap.subscribe(params => {
      if (params.get('OMDBId')){
      this.OMDBId = new String(params.get('OMDBId'));
      }
      this.validate_input()
      this.populteMovie()
      this.populateAvg()
    })
  }

}
