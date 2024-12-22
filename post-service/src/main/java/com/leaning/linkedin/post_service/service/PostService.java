package com.leaning.linkedin.post_service.service;

import com.leaning.linkedin.post_service.dto.PostCreateRequestDto;
import com.leaning.linkedin.post_service.dto.PostDto;
import com.leaning.linkedin.post_service.entity.Post;
import com.leaning.linkedin.post_service.exception.ResourceNotFoundException;
import com.leaning.linkedin.post_service.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public PostDto createPost(PostCreateRequestDto postCreateRequestDto, Long userId) {
        Post post = modelMapper.map(postCreateRequestDto, Post.class);
        post.setUserId(userId);
        Post savedPost = postRepository.save(post);
        return modelMapper.map(savedPost, PostDto.class);

    }

    public PostDto getPostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found for the given Id"));
        return modelMapper.map(post, PostDto.class);
    }

    public List<PostDto> getAllPostsOfUser(Long userId) {
        List<Post> posts = postRepository.findByUserId(userId);
        return posts.stream().map((element) -> modelMapper.map(element, PostDto.class)).collect(Collectors.toList());
    }
}
