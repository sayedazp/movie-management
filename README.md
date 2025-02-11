# Movie Management Application

This is a Movie Management Application built using **Angular**, **Spring Boot**, and an **H2 Database**. The application provides functionalities for both admin and regular users to manage and view movies, including features such as adding, deleting, rating movies, and searching movies.

## Table of Contents

- [Project Overview](#project-overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [System Requirements](#system-requirements)
- [Setup Instructions](#setup-instructions)
  - [Backend (Spring Boot)](#backend-spring-boot)
  - [Frontend (Angular)](#frontend-angular)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
  - [Admin APIs](#admin-apis)
  - [User APIs](#user-apis)
- [Database](#database)
- [License](#license)

## Project Overview

This application consists of two main parts:

1. **Admin Dashboard:**
   - Admin users can add or remove movies to/from the database.
   - Admin can add movies individually or in bulk.
   - Admin can delete movies from the database.
   - Admin can rate movies.

2. **Regular User Dashboard:**
   - Regular users can view the list of movies added by the admin.
   - Users can view detailed information about each movie.
   - Users can rate movies.
   - Users can search for movies by title.

### Bonus Features:
- Batch adding/removing movies by admin.
- Pagination in the movie list.
- Movie ratings by users.

## Features

- **Admin Functions:**
  - Login functionality for the admin.
  - Add movies individually (`/movies/add`) or in bulk (`/movies/add-all`).
  - Delete movies individually (`/movies/delete/{id}`) or in bulk (`/movies/delete/all`).
  - Rate movies using `/movies/rate`.
  - View and manage the movie list with pagination.

- **User Functions:**
  - Login functionality for regular users.
  - View the full list of movies with pagination.
  - View detailed information about each movie.
  - Rate movies.
  - Search for movies by title.

- **Nice to Have:**
  - Pagination of movie list.
  - Movie search by title.
  
## Technologies Used

- **Backend:** Spring Boot, Java 17
- **Frontend:** Angular 16+
- **API:** OMDB API (https://www.omdbapi.com/)
- **Database:** H2 Database (in-memory)
  
## System Requirements

- Java 17+ (For the Spring Boot application)
- Node.js and npm (For the Angular frontend)
- Angular CLI (For building the Angular frontend)
- H2 Database (in-memory database for simplicity)

## Setup Instructions

### Backend (Spring Boot)

1. Clone the repository:
   ```bash
   git clone https://github.com/<username>/movie-management-app.git
   cd movie-management-app
