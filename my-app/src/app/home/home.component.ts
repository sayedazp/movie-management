import { Component, OnInit, Renderer2 } from '@angular/core';
import { Router, RouterLink, RouterModule, RouterOutlet } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { CommonModule } from '@angular/common';
import { AuthenticatedUser } from '../../types';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterModule, CommonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit{
  constructor(private router: Router, private authService:AuthService,private renderer: Renderer2) {}
  user:AuthenticatedUser | null = null;
  isAdmin():boolean{
    return this.authService.isAdmin()
  }
  toggleDarkMode(): void {
    const htmlElement = document.documentElement;
    if (htmlElement.classList.contains('dark')) {
      this.renderer.removeClass(htmlElement, 'dark');
    } else {
      this.renderer.addClass(htmlElement, 'dark');
    }
  }
  applyDarkMode(): void {
    const htmlElement = document.documentElement;
    this.renderer.addClass(htmlElement, 'dark');
  }

  addDash(){
    this.router.navigate(['/dashboard']);
  }
  setUser(){
    console.log(this.authService.getCurrentUser())
    return this.user = this.authService.getCurrentUser()
  }
  ngOnInit(): void {
    this.setUser()
    console.log("js is here");
    this.applyDarkMode();
  }
}
