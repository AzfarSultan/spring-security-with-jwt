package com.example.securitydemo.review;

import java.util.List;

public interface ReviewService {

    List<Review> getAllReview(Long companyId);
    boolean saveReview(Long companyId, Review review);
}
