package com.leaning.linkedin.post_service.controller;

import com.leaning.linkedin.post_service.auth.UserContextHolder;
import com.leaning.linkedin.post_service.dto.PostCreateRequestDto;
import com.leaning.linkedin.post_service.dto.PostDto;
import com.leaning.linkedin.post_service.service.PostService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping()
    public ResponseEntity<PostDto> createPost(@RequestBody PostCreateRequestDto postCreateRequestDto) {
        Long userId = UserContextHolder.getCurrentUserId();
        return new ResponseEntity<>(postService.createPost(postCreateRequestDto, userId), HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long postId) {
        return new ResponseEntity<>(postService.getPostById(postId), HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/allPosts")
    public ResponseEntity<List<PostDto>> getAllPostsOfUser(@PathVariable Long userId) {
        return new ResponseEntity<>(postService.getAllPostsOfUser(userId), HttpStatus.OK);
    }
}
