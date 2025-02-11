import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router, RouterModule, RouterOutlet } from '@angular/router';
import { OMBDMovie } from '../../../types';
import { MoviesService } from '../../services/movies.service';
import { concat } from 'rxjs';
import { FormsModule } from '@angular/forms';
import { MovieDetailComponent } from "../movie-detail/movie-detail.component";
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-movie-management',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './movie-management.component.html',
  styleUrl: './movie-management.component.scss'
})
export class MovieManagementComponent implements OnInit{
  constructor(private moviesService: MoviesService, private authService:AuthService, private router: Router) {}
  
  movies:OMBDMovie[] = []
  selected = new Set<String>();
  currentPage = 0;
  pageSize = 10;
  totalItems = 0;
  totalPages = 0;
  searchTerm = "";
  
  isAdmin(){
    return this.authService.isAdmin();
  }
  onRemoveMovie(movie:OMBDMovie){
    console.log(movie)
    if (movie.id != undefined){
      console.log("entered")
    this.moviesService.deleteMovie(movie.id).subscribe(
      (response)=>{
        this.movies.forEach( (m, index) => {
          if(m.id === movie.id) this.movies.splice(index,1);
        });
      }
    )
    }
  }

  onDeleteMovies(){
    let ids:Number[] = []
     this.movies.filter((m, index)=>{
      console.log(m)
      return this.selected.has(m.imdbID)
    }).forEach((m, i)=> {
      console.log(m)
      if (m.id != undefined){
      ids.push(m.id)
      }
    })
    console.log(ids)
    if(ids!=undefined){
      this.moviesService.deleteMovies(ids).subscribe(
        (response)=>{
          let new_movies = this.movies.filter(
            (m, i)=>{
              return !this.selected.has(m.imdbID);
            }
          )
          this.movies = new_movies;
          this.selected.clear();
        }
      )
    }
  }


  onSelectMovie(movie:OMBDMovie){
    if (this.selected.has(movie.imdbID)){
      this.selected.delete(movie.imdbID)
    }else{
    this.selected.add(movie.imdbID)
    }
  }


  ngOnInit(): void {
    this.applyTheme()
    this.fetchMovies()
  }
  hasNextPage():boolean{
    return this.currentPage < this.totalPages - 1
  }

  hasPrevPage():boolean{
    return this.currentPage > 0;
    
  }

  previousPage(): void {
    if (this.hasPrevPage()) {
      this.currentPage--;
      if (this.searchTerm){
        this.onSearchMovies();
      }else{
        this.fetchMovies();
      }

    }
  }

  nextPage(): void {
    if (this.hasNextPage()) {
      this.currentPage++;
      if (this.searchTerm){
        this.onSearchMovies();
      }else{
        this.fetchMovies();
      }
    }
  }

  onSearchMovies():void{
    console.log(this.searchTerm)
    if (this.searchTerm==""){
      this.fetchMovies()
    }
    else{
    this.moviesService.searchAllMoviesPaginated(this.currentPage, this.pageSize, this.searchTerm).subscribe(
      (response)=>{
            this.movies = response.content;
            console.log(this.movies);
            this.totalItems = response.totalItems;
            this.totalPages = response.totalPages;
          }
    )
  }
  }

  fetchMovies():void{
    this.moviesService.getAllMoviesPaginated(this.currentPage, this.pageSize).subscribe(
      (response)=>{
            this.movies = response.content;
            console.log(this.movies);
            this.totalItems = response.totalItems;
            this.totalPages = response.totalPages;
          }
    )
  }
  navigateToMovie(OMDBId:String, dbId:Number){
    console.log(OMDBId)
    console.log("asd")
      this.router.navigate(['home/app-movie-detail', dbId], {
        queryParams: { OMDBId: OMDBId }
      });    
  }

  applyTheme():void{

    var listView = document.querySelector('.list-view');
    var gridView = document.querySelector('.grid-view');
    var projectsList = document.querySelector('.project-boxes');
    
    listView?.addEventListener('click', function () {
      gridView?.classList.remove('active');
      listView?.classList.add('active');
      projectsList?.classList.remove('jsGridView');
      projectsList?.classList.add('jsListView');
    });
    
    gridView?.addEventListener('click', function () {
      gridView?.classList.add('active');
      listView?.classList.remove('active');
      projectsList?.classList.remove('jsListView');
      projectsList?.classList.add('jsGridView');
    });
  }

}
