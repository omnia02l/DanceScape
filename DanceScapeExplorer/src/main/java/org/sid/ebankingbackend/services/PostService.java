package org.sid.ebankingbackend.services;

import org.sid.ebankingbackend.entities.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {

    String createPost(String userName, Post post);

    List<Post> myPosts(String userName);

    List<Post> listPosts(String userName);

    String addComment(String userName, Long postId, Comment comment);

    String deletePost(Long id);

    String deleteComment(Long commentId);

    String like(Long id, String userName);

    String dislike(Long id, String userName);

    void addImage(MultipartFile image, Long id) throws IOException;

    PostImage getImageWithPostId(Long postId);

    MyPostStats getMyPostStats(String username);

    AllPostStats getAllPostsStats();
}
