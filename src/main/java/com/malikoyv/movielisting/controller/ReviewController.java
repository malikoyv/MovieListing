    package com.malikoyv.movielisting.controller;

    import com.malikoyv.movielisting.model.Review;
    import com.malikoyv.movielisting.service.ReviewService;
    import org.bson.types.ObjectId;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.access.prepost.PreAuthorize;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;
    import java.util.Optional;

    @RestController
    @RequestMapping("/reviews")
    public class ReviewController {
        @Autowired
        private ReviewService reviewService;

        @GetMapping("/getByMovie/{id}")
        @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
        public ResponseEntity<List<Review>> getByMovie(@PathVariable("id") ObjectId movieId) {
            Optional<List<Review>> reviews = reviewService.getByMovieId(movieId);
            return reviews.map(v -> new ResponseEntity<>(v, HttpStatus.FOUND))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

        @GetMapping("/getByAuthor/{id}")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<List<Review>> getByAuthor(@PathVariable("id") ObjectId authorId) {
            Optional<List<Review>> reviews = reviewService.getByAuthorId(authorId);
            return reviews.map(v -> new ResponseEntity<>(v, HttpStatus.FOUND))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

        @PostMapping("/addReview")
        @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
        public ResponseEntity<Review> createReview(@RequestBody Review review) {
            if (reviewService.addReview(review) != null){
                return new ResponseEntity<>(reviewService.addReview(review), HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        @PutMapping("/updateReview/{id}/{description}")
        @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
        public ResponseEntity<Review> updateReview(@PathVariable ObjectId id, @PathVariable String description) {
            if (reviewService.updateReview(id, description) != null) {
                return new ResponseEntity<>(reviewService.updateReview(id, description), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        @DeleteMapping("/deleteReview/{id}")
        @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
        public ResponseEntity<HttpStatus> deleteReview(@PathVariable ObjectId id) {
            if (reviewService.deleteReview(id)) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
