# Movie listing Application
This is a Movie Listing Application built using Spring Boot, MongoDB, Spring Security, and JWT. The application provides RESTful CRUD APIs to manage movies, reviews, recommendations, watch history, and watchlists. The application also supports user authentication and authorization using JWT.

## Features
- User authentication and authorization with JWT
- CRUD operations for movies, reviews, recommendations, watch history, and watchlists
- Role-based access control
- User profile management
## Technologies
- Spring Boot
- MongoDB
- Spring Security
- JWT
- Maven
## Prerequisites
Java 11 or higher
Maven 3.6.0 or higher
MongoDB 4.0 or higher
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
### Authentication
#### Register
<img src="https://github.com/malikoyv/MovieListing/assets/124885789/0214ab65-f074-4f2d-82fb-f28901bf992a" height=200px>

#### Login
<img src="https://github.com/malikoyv/MovieListing/assets/124885789/25b81b68-f9a6-4917-a80b-795a697c926b" height=200px>

### Users
Authorize before each request, where it is needed
#### Get All Users
<img src="https://github.com/malikoyv/MovieListing/assets/124885789/79defd30-0992-4af5-b98e-70ea238a925d" height=50px>

#### Get Users By Id
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
<img src="https://github.com/malikoyv/MovieListing/assets/124885789/7918c393-3061-47ba-b76f-3496da52e772" height=50px>
