package com.storyreadingbe.service;

import com.storyreadingbe.dto.response.ReviewResponseDTO;
import com.storyreadingbe.dto.response.UserResponseDTO;
import com.storyreadingbe.entity.Review;
import com.storyreadingbe.entity.User;
import com.storyreadingbe.repository.ReviewRepository;
import com.storyreadingbe.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ReviewServiceImpl {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    public void createReview(String content, String rating, String username, Integer idBook) {
        try {
            Review review = reviewRepository.findReviewByUsernameAndIdBook(username, idBook);
            if (review == null)
                review = new Review();
            review.setContent(content);
            review.setRating(rating);
            review.setUsername(username);
            review.setIdBook(idBook);
            reviewRepository.save(review);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Something went wrong");
        }
    }

    public List<ReviewResponseDTO> getAllReview(Integer idBook) {
        List<Review> list = reviewRepository.findAllByIdBook(idBook);
        List<ReviewResponseDTO> res = new ArrayList<>();
        for (Review review : list) {
            ReviewResponseDTO dto = modelMapper.map(review, ReviewResponseDTO.class);
            User user = userRepository.findById(review.getUsername()).get();
            dto.setFullname(user.getFullname());
            dto.setUrlImg(user.getUrlImg());
            res.add(dto);
        }
        return res;
    }
}