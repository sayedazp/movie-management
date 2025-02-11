import { AfterViewInit, Component, ElementRef, OnInit, Renderer2 } from '@angular/core';
import { MoviesService } from '../../services/movies.service';
import { MoviesSearchReturn, MovieTypes, OMBDMovie, ReturnDataTypes, SearchFilters } from '../../../types';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-adding-new',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './adding-new.component.html',
  styleUrl: './adding-new.component.scss'
})
export class AddingNewComponent implements OnInit, AfterViewInit{

  constructor(private movieServices: MoviesService, private renderer: Renderer2, private el: ElementRef) {}
  viewClass: string = 'jsGridView'; 
  viewType: 'list' | 'grid' = 'grid';
  setView(view: 'list' | 'grid'): void {
    this.viewType = view; 
    this.viewClass = view === 'grid' ? 'jsGridView' : 'jsListView';
  }
  ngAfterViewInit(): void {
    const listView = this.el.nativeElement.querySelector('.list-view');
    const gridView = this.el.nativeElement.querySelector('.grid-view');
    const projectsList = this.el.nativeElement.querySelector('.project-boxes');
    
    this.renderer.listen(listView, 'click', () => {
      this.renderer.removeClass(gridView, 'active');
      this.renderer.addClass(listView, 'active');
      this.renderer.removeClass(projectsList, 'jsGridView');
      this.renderer.addClass(projectsList, 'jsListView');
    });

    this.renderer.listen(gridView, 'click', () => {
      this.renderer.addClass(gridView, 'active');
      this.renderer.removeClass(listView, 'active');
      this.renderer.removeClass(projectsList, 'jsListView');
      this.renderer.addClass(projectsList, 'jsGridView');
    });
  }
  selected = new Set<String>();
  filters:SearchFilters = {
    s: '',  
    y: '',  
    type: MovieTypes.Any, 
    r:ReturnDataTypes.Json
  };
  
  movies:OMBDMovie[] = []


  onSelectMovie(movie:OMBDMovie){
    if (this.selected.has(movie.imdbID)){
      this.selected.delete(movie.imdbID)
    }else{
    this.selected.add(movie.imdbID)
    }
  }
  onAddMovie(movie:OMBDMovie){
    this.movieServices.addMovie(movie).subscribe(
      (res)=>{
        console.log(res);
        movie.Persisted = true
      }
    )
  }
  onAddMovies(ids:Set<String>){
    let movies = this.movies.filter((m)=>ids.has(m.imdbID))
    console.log(movies)
    this.AddMovies(movies)
  }
  AddMovies(movies:OMBDMovie[]){
    return this.movieServices.addMovies(movies).subscribe(
      (res)=>{
        console.log(res);
        movies.map(m => m.Persisted=true);
        this.selected.clear()
        // movie.Persisted = true
      }
    )
  }
  
  onSubmit(){
    this.movieServices.searchMoviesOMDB(this.filters).subscribe(
      (res)=> {
        this.movies = res.Search
        this.selected.clear()
      })
  }
  ngOnInit(): void {
    console.log("init")
  }
}
