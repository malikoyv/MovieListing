# Movie listing Application
This is a Movie Listing Application built using Spring Boot, MongoDB, Spring Security, and JWT. The application provides RESTful CRUD APIs to manage movies, reviews, recommendations, watch history, and watchlists. The application also supports user authentication and authorization using JWT.

## Features
- User authentication and authorization with JWT
- CRUD operations for movies, reviews, recommendations, watch history, and watchlists
- Role-based access control
- User profile management
- Unit testing for controllers and services
## Technologies
- Spring Boot
- MongoDB
- Spring Security
- JWT
- Maven
- Mockito
## Prerequisites
- Java 11 or higher
- Maven 3.6.0 or higher
- MongoDB 4.0 or higher
## Getting Started
### Clone the repository:
```
git clone https://github.com/coma123/MovieListingApplication.git
```
Navigate to the created directory:
```
cd MovieListingApplication
```
## Configure MongoDB:
  - Create a MongoDB database for the application.
- Update application properties:
  - Update the MongoDB URI in **'src/main/resources/application.properties'**
```
spring.data.mongodb.uri=mongodb://<username>:<password>@localhost:27017/movielisting
```
## Build and run the application
Use Maven to build and run the application:
```
mvn spring-boot:run
```
The application will start running at http://localhost:8080.

## API Endpoints
Send API requests using Postman. Authorize before each request, where it is needed
### Authentication
#### Register
<img src="https://github.com/malikoyv/MovieListing/assets/124885789/0214ab65-f074-4f2d-82fb-f28901bf992a" height=200px>

#### Login
<img src="https://github.com/malikoyv/MovieListing/assets/124885789/25b81b68-f9a6-4917-a80b-795a697c926b" height=200px>

### Users
#### Get All Users
<img src="https://github.com/malikoyv/MovieListing/assets/124885789/79defd30-0992-4af5-b98e-70ea238a925d" height=50px>

#### Get Users By ID
<img src="https://github.com/malikoyv/MovieListing/assets/124885789/83ec5c16-1673-4341-b855-e00a8c8950a5" height=200px>

#### Get Users By Username
<img src="https://github.com/malikoyv/MovieListing/assets/124885789/a93db947-daec-4a4d-a720-28acd3adce96" height=50px>

#### Add User
<img src="https://github.com/malikoyv/MovieListing/assets/124885789/3d15a6dd-b057-403c-aba9-49ff1a942785" height=200px>

#### Update User's username
<img src="https://github.com/malikoyv/MovieListing/assets/124885789/ac40e0bc-b55c-4c13-8892-88b98191c48d" height=50px>

#### Update User's password
<img src="https://github.com/malikoyv/MovieListing/assets/124885789/bec0c2fa-52e9-4019-9590-6e2905e03a50" height=50px>

#### Delete User
<img src="https://github.com/malikoyv/MovieListing/assets/124885789/024fb7b1-2522-4a05-8e3c-76a654dfe52c" height=50px>

### Movies
#### Get All Movies
<img src="https://github.com/malikoyv/MovieListing/assets/124885789/b2846250-db2c-4132-84da-45993d8244e0" height=50px>

#### Get Movie By ID
<img src="https://github.com/malikoyv/MovieListing/assets/124885789/00d8953b-be8d-48b2-9b01-a01581a26f94" height=50px>

#### Get Movie By Title
<img src="https://github.com/malikoyv/MovieListing/assets/124885789/5605c26f-f2b6-4d93-8dd9-31e8121bc06b" height=50px>

#### Get Movie By Director
<img src="https://github.com/malikoyv/MovieListing/assets/124885789/d84e01c9-d21a-4ae5-8541-12fcefd5c773" height=50px>

#### Add Movie
<img src="https://github.com/malikoyv/MovieListing/assets/124885789/3930d256-3f9b-49af-9ea2-d103dd5be05f" height=200px>

#### Update Movie
<img src="https://github.com/malikoyv/MovieListing/assets/124885789/1a6e114a-b0ed-45be-bfab-12afcdf0db77" height=200px>

#### Delete Movie
<img src="https://github.com/malikoyv/MovieListing/assets/124885789/b26c4947-9d9c-4858-8d04-a9c3431bd885" height=50px>

### Reviews
#### Get a Review By Movie ID
<img src="https://github.com/malikoyv/MovieListing/assets/124885789/d30ff084-a622-420a-8a21-ed7e261b1bad" height=50px>

#### Get a Review By Author ID
<img src="https://github.com/malikoyv/MovieListing/assets/124885789/ed0af0ab-c1fa-41e2-9afe-e8780eacce2a" height=50px>

#### Add a Review
<img src="https://github.com/malikoyv/MovieListing/assets/124885789/cfa043f5-2e6a-43b8-b483-460570667c57" height=200px>

#### Update a Review's description
<img src="https://github.com/malikoyv/MovieListing/assets/124885789/96696fb2-49f3-45d4-9caf-4885c9062a56" height=50px>

#### Delete a Review
<img src="https://github.com/malikoyv/MovieListing/assets/124885789/cce4ca2b-bf35-4357-bffb-5596c1f6c002" height=50px>

### Watchlist
#### Get All Watchlists
<img src="https://github.com/malikoyv/MovieListing/assets/124885789/ff8aabd9-d963-4148-9790-1458049ed563" height=50px>

#### Get Movies From a Watchlist
<img src="https://github.com/malikoyv/MovieListing/assets/124885789/e1fa113c-bc7b-4950-bbf9-b138e7208e46" height=50px>

#### Create a Watchlist
<img src="https://github.com/malikoyv/MovieListing/assets/124885789/3f18e178-95ec-4386-9a21-cf0761e5a77e" height=200px>

#### Add a Movie to a Watchlist
<img src="https://github.com/malikoyv/MovieListing/assets/124885789/82e0e12c-9b88-476c-84c1-7c2fb8308dcf" height=50px>

#### Delete a Movie from a Watchlist
<img src="https://github.com/malikoyv/MovieListing/assets/124885789/26b82fe1-c056-424b-813e-b722ae73b59e" height=50px>

#### Delete a Watchlist
<img src="https://github.com/malikoyv/MovieListing/assets/124885789/887cfd42-a3dd-4678-aae9-02bdb6fbe00a" height=50px>

### Watch history
#### Get a Watch history By User ID
<img src="https://github.com/malikoyv/MovieListing/assets/124885789/493224be-13ca-4b9a-854b-a9f7b7f15c73" height=50px>

#### Add a Watch history
<img src="https://github.com/malikoyv/MovieListing/assets/124885789/23debd87-12e6-4cf1-b8fd-ec47447519fc" height=200px>

### Recommendations
#### Get Recommendations By User ID
<img src="https://github.com/malikoyv/MovieListing/assets/124885789/2b915552-ae31-43bc-9d21-04fd8f3bffd1" height=50px>

## Running Tests
To run the unit tests for the application, use the following command:
```
mvn test
```
Unit tests are included for the controllers and services to ensure the correctness of the application logic.

## Contribution
I would be glad if you contribute to my project! Follow these steps to contribute:

- Fork the repository.
- Create a new branch (git checkout -b feature-branch).
- Make your changes.
- Commit your changes (git commit -m 'Add some feature').
- Push to the branch (git push origin feature-branch).
- Create a new Pull Request.
Thank you for your contributions!

## License
This project is licensed under the GNU Public Licence - see the [LICENSE](https://github.com/malikoyv/MovieListing/blob/main/LICENSE) file for details.

### Thank you for using and contributing to the Movie Listing Application!
