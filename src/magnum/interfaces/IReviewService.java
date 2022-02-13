/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magnum.interfaces;

import java.util.List;
import magnum.models.Review;

/**
 *
 * @author Litai
 */
public interface IReviewService {
    
       public boolean addReview(Review e);
    public boolean updateReview(Review e);
    public boolean removeReview(Review e);
    public List<Review> getAll();
    public Review getReview(int id);
    
}
