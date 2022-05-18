package com.druid.interfaces;

import com.druid.models.Review;

import java.util.List;

public interface IReviewService {
    public boolean addReview(Review e);
    public boolean updateReview(Review e);
    public boolean removeReview(Review e);
    public List<Review> getAll();
    public Review getReview(int id);
    public Review getReviewUser(int userid);
}
