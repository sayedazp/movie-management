export interface User{
    email:String,
    password:String
}
// export interface AuthenticatedUser{
//     username:String,
//     email:String,
//     token:String
// }

export interface Role {
    authority: RoleTypes; 
  }
export interface AuthenticatedUser {
    email: string;
    role: Role[];
  }
export enum RoleTypes{
    ADMIN = 'ROLE_ADMIN',
    CLIENT = 'ROLE_USER'
}
export enum MovieTypes {
    Movie = 'movie',
    Series = "series",
    Episode = "episode",  
    Any=""
};

export enum ReturnDataTypes {
    Json = "json",
    Xml = "xml"
}
export interface SearchFilters{
    s:String,  // movie title
    y:String,  // year
    type:MovieTypes, // genre
    r:ReturnDataTypes, // 
}

export interface OMBDMovie{
    id?:Number,
    Title:String,
    Year:String,
    imdbID:String,
    Type:String,
    Poster:String,
    Persisted?:boolean
}

export interface MoviesSearchReturn{
    Search:OMBDMovie[]
}

export interface MovieDetailed{
    Title: string;
    Year: string;
    Rated: string;
    Released: string;
    Runtime: string;
    Genre: string;
    Director: string;
    Writer: string;
    Actors: string;
    Plot: string;
    Language: string;
    Country: string;
    Awards: string;
    Poster: string;
    Ratings: Array<{
        Source: string;
        Value: string;
    }>;
    Metascore: string;
    imdbRating: string;
    imdbVotes: string;
    imdbID: string;
    BoxOffice: string;
    Website: string;
    Response: string;
}


export interface ErrorResponse {
    statusCode: number;    
    message: string;        
    error?: string;         
    timestamp?: string;     
    path?: string;
  }

  export interface RatedMovie{
    movieId:Number,
    rating:Number,
  }

  export interface AverageMovieRate{
    movieId:Number,
    avgRating:Number
  }