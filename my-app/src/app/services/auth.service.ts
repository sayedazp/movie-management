import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthenticatedUser, ErrorResponse, RoleTypes, User } from '../../types';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/'
  private  login_endpoint = "login";
  constructor(private httpClient:HttpClient) { 
  }
  getCurrentUser():AuthenticatedUser{
    let data:string | null = localStorage.getItem("user_data")
    if(data){
      return JSON.parse(data);
    }
    throw(() => "No user found");
  }
  removeAuthData():void{
    if(localStorage.getItem("user_data")){
      localStorage.removeItem("user_data")
    }
  }
  isAuthenticatedSoft():boolean{
    let data:string | null = localStorage.getItem("user_data")
    if(data){
      let authUser:AuthenticatedUser = JSON.parse(data);
      if(authUser.email){
        return true
      }
    }
    return false
  }
  isAuthenticated():Observable<boolean>{
    let data:string | null = localStorage.getItem("user_data")
    
    if (!data){
      return new Observable(observer => {
        observer.next(false);
        observer.complete();
      });
    }
    
    let authUser:AuthenticatedUser = JSON.parse(data);
    const params = new HttpParams()
          .set('mail', authUser.email)
    let ret = this.httpClient.get<boolean>(`${this.apiUrl}auth/is-auth`, { params, withCredentials: true });
    ret.forEach((val)=>{
      if (!val){
        this.removeAuthData()
      }
    })
    return ret
  }
  isAdmin():boolean{
    let data:string | null = localStorage.getItem("user_data")
    
    if (!data){
      return false;
    }
    let authUser:AuthenticatedUser = JSON.parse(data);
    if (authUser.role.map((k, i)=>k.authority).includes(RoleTypes.ADMIN)){
      return true
    }
    return false
  }
  login(user:User):Observable<AuthenticatedUser>{
    return this.httpClient.post<AuthenticatedUser>(this.apiUrl
    .concat(this.login_endpoint), user, {
      withCredentials: true
  }).pipe(catchError(this.handleError))
  }
  private handleError(error: HttpErrorResponse):Observable<never> {
    let errorMessage:ErrorResponse ;
    if (error.status === 0) {
      errorMessage = {
        message:error.message,
        statusCode:error.status
      }
      
    } else if(error.error){
      errorMessage = {
        message:error.error.message,
        statusCode:error.error.status
      }

    }else {
      errorMessage = {
        message:"unknown error please try again",
        statusCode:error.status
      }
    }
    return throwError(() => errorMessage);
    ;
  }
}
