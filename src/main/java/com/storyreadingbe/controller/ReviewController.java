package com.storyreadingbe.controller;

import com.storyreadingbe.common.URLConst;
import com.storyreadingbe.dto.response.ReviewResponseDTO;
import com.storyreadingbe.entity.Review;
import com.storyreadingbe.service.ReviewServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReviewController {
    @Autowired
    ReviewServiceImpl commentService;
    
    @PostMapping(value = URLConst.Author.CREATE_REVIEW_BOOK)
    public void createReview(@RequestParam("content") String content, @RequestParam("rating") String rating,
                                      @RequestParam("username") String username, @RequestParam("idBook") Integer idBook) {
        commentService.createReview(content, rating, username, idBook);
    }
    
    @GetMapping(value = URLConst.User.ALL_REVIEW + "/{idBook}")
    public List<ReviewResponseDTO> getAllReview(@PathVariable Integer idBook) {
        return commentService.getAllReview(idBook);
    }
    
}
