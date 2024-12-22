package com.leaning.linkedin.post_service.service;

import com.leaning.linkedin.post_service.entity.PostLike;
import com.leaning.linkedin.post_service.exception.BadRequestException;
import com.leaning.linkedin.post_service.exception.ResourceNotFoundException;
import com.leaning.linkedin.post_service.repository.LikeRepository;
import com.leaning.linkedin.post_service.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    public void likePost(Long postId) {
        log.info("Attempting to like the post with id: {}", postId);
        postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("No Post found with the given id"));
        boolean alreadyLiked = likeRepository.existsByUserIdAndPostId(1L, postId);
        if (alreadyLiked) throw new BadRequestException("Post is already liked");

        PostLike postLike = new PostLike();
        postLike.setPostId(postId);
        postLike.setUserId(1L);
        likeRepository.save(postLike);

        log.info("Post liked successfully");
    }


    @Transactional
    public void unlikePost(Long postId) {
        log.info("Attempting to un-like the post with id: {}", postId);
        postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("No Post found with the given id"));
        boolean alreadyLiked = likeRepository.existsByUserIdAndPostId(1L, postId);
        if (!alreadyLiked) throw new BadRequestException("Post is not liked");
        likeRepository.deleteByUserIdAndPostId(1L, postId);

        log.info("Post unliked successfully");
    }
}
