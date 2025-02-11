import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { MovieManagementComponent } from './home/movie-management/movie-management.component';
import { AddingNewComponent } from './home/adding-new/adding-new.component';
import { NgModule } from '@angular/core';
import { MovieDetailComponent } from './home/movie-detail/movie-detail.component';
import { authGuardGuard } from './home/guards/auth-guard.guard';
import { adminGuardGuard } from './home/guards/admin-guard.guard';

export const routes: Routes = [
    {
        path:'home',
        component:HomeComponent,
        canActivate:[authGuardGuard],
        children:[
            {
                path:"app-movie-detail/:id", //admin + client
                component:MovieDetailComponent,
            },
            {
                path:'manage-movies', // admin + client
                component:MovieManagementComponent,
            },
            {
                path:'add-movies', //admin
                component:AddingNewComponent,
                canActivate:[adminGuardGuard]
            },
            {
                 path: '', redirectTo: 'manage-movies', pathMatch: 'full' ,  //admin

            }
        ]
    },
    {
        path:'login',
        component:LoginComponent
    },
    
    { path: '', redirectTo: '/login', pathMatch: 'full' }, 
];
