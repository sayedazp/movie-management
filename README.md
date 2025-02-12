
# Movie Management Application

  

This is a Movie Management Application built using **Angular**, **Spring Boot**, and an **H2 Database**. The application provides functionalities for both admin and regular users to manage and view movies, including features such as adding, deleting, rating movies, and searching movies.

  
  

## Project Overview

  

This application consists of two main parts:

  

1.  **Admin Dashboard:**

- Admin can basically login into the system via `/login` -> not a controller based authentication, it's based on spring security default bhaviour by exposing a filter named `UserPasswordAuthenticationFilter` (I tried this approach for experimenting puropses as the traditional way especially when using JWT based authentication includes using normal controller)

-  `UserPasswordAuthenticationFilter` created a challenge on how to treat errors that appears during the auth process which was very interesting `The filters happen before requests are passed to controller` so controller advice would not work by default in this scenario, So I found a work around by creating a resolver.

  

- Admin users can add or remove movies to/from the database.

  

- Admin can add movies individually or in bulk. (what if movie was already added ?), that would duplicate the movie external id (imdb id)

  

- Admin can delete movies from the database individually or bulk.(batch deletions bypass the persistence context and doesn't propagate to related entities!)

  

- Admin can rate movies.

  

2.  **Regular User Dashboard:**

- Regular users can login also :).

- Regular users can view the list of movies added by the admin.

- Users can view detailed information about each movie.

- Users can rate movies.

- Users can see average movie rates.

- Users can search for movies by title or year.

  

### Bonus :

- Batch adding/removing movies by admin. [done]

- Pagination in the movie list. [done]

- Movie ratings by users. [done]

- Swagger Api docs, can be accessed at `/swagger-ui/index.html`

  

## Technologies Used

  

-  **Backend:** Spring Boot, Java 17

-  **Frontend:** Angular 16+, TypeScript

-  **Database:** H2 Database (in-memory)

-  **API Docs**: OpenApi

## System Requirements

  

- Java 17+ (For the Spring Boot application)

- Node.js and npm (For the Angular frontend)

- Angular CLI (For building the Angular frontend)

- H2 Database (in-memory database for simplicity)

  

## Setup Instructions

  

### Backend (Spring Boot)

  

1. Clone the repository:

```bash

cd movie-management

```

2. Navigate to backend:

```bash

cd fawry-task

```

3.Build and run the Spring Boot application:

```

mvn spring-boot:run

```

4.Configure admin, client username and password in 
 ```
application.properties file

 ```
 
 5.Run the bckend via
 ```
mvn spring-boot:run
```

  

The backend will be available at http://localhost:8080.

  

### Frontend (Angular)

  
  

1. Navigate to the `frontend` directory.

  

2. Install the required dependencies:

- Run `npm install` to install all the necessary npm packages listed in the `package.json` file.

  

3. Define `omdbApiKey` at `src/constants`.

  

4. Build and serve the Angular application:

- Use the `ng serve` command to build and serve the Angular application. This will launch the app and it will be available at `http://localhost:4200`.

  

## Running the Application

  

1. Start the backend application (Spring Boot).

2. Start the frontend application (Angular).

3. Access the application in your browser at `http://localhost:4200`.