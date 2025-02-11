import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule, NgForm, ReactiveFormsModule }   from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { ErrorResponse, RoleTypes } from '../../types';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements OnInit{
  email: string = '';
  password: string = '';
  error?:ErrorResponse;
  constructor(private router: Router, private loginService:AuthService) {

  }

  ngOnInit(): void {
    if (this.loginService.isAuthenticatedSoft()){
      this.router.navigate(['/home/manage-movies']);
    }
  }
  checkAuth(){
  }
  onSubmit() {
    // Implement your login logic here
    console.log('Username:', this.email);
    console.log('Password:', this.password);
    let user = {
      'email':this.email,
      "password":this.password
    }
    this.loginService.login(user).pipe().subscribe(
      (authUser)=>{
        localStorage.setItem("user_data", JSON.stringify(authUser))
        console.log(JSON.stringify(authUser))
        console.log(authUser.role[0].authority)
        switch (authUser.role[0].authority) {
          case RoleTypes.ADMIN:
            this.router.navigate(['/home/add-movies']);
            return
          case RoleTypes.CLIENT:
            this.router.navigate(['/home/manage-movies']);
            return
          default :
            console.log(authUser)
        }
      },
      (err)=>{
        this.error = err;
        console.log(err)
      }
    )
  }  
}
