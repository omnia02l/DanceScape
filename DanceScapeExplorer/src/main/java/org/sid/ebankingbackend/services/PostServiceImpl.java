package org.sid.ebankingbackend.services;

import org.sid.ebankingbackend.entities.*;
import org.sid.ebankingbackend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    PostRepo postRepo;
    @Autowired
    CommentRepo commentRepo;
    @Autowired
    LikeRepo likeRepo;
    @Autowired
    DislikeRepo dislikeRepo;
    @Autowired
    PostImageRepo postImageRepo;

    @Autowired
    BadWordsRepo badWordsRepo;

    @Override
    public String createPost(String userName, Post post) {
        post.setUserName(userName);
        this.postRepo.save(post);
        return "Post saved";
    }

    @Override
    public List<Post> myPosts(String userName) {
        return this.postRepo.myPosts(userName);
    }

    @Override
    public List<Post> listPosts(String userName) {
        return this.postRepo.findAll();
    }

    @Override
    public String addComment(String userName, Long postId, Comment comment) {
        Post post = this.postRepo.findById(postId).get();
        List<String> badWords = this.badWordsRepo.getWordsList();
        for(String w : badWords){
            if(comment.getText().contains(w)){
                return "Bad word detected";
            }
        }
        comment.setPost(post);
        comment.setUserName(userName);
        this.commentRepo.save(comment);
        return "Comment created";
    }

    @Override
    public String deletePost(Long id) {
        this.postRepo.deleteById(id);
        return "Post deleted";
    }

    @Override
    public String deleteComment(Long commentId) {
        this.commentRepo.deleteById(commentId);
        return "Comment deleted";
    }

    @Override
    public String like(Long id, String userName) {
        Optional<Dislikes> dislike = this.dislikeRepo.getDislike(id, userName);
        if (dislike.isPresent()) {
            this.dislikeRepo.delete(dislike.get());
            this.incrementLikesAndDecrementDislikes(id);
            this.addLike(id, userName);
            return "ok";
        }
        if (likeRepo.getLikeNumber(id, userName) == 0) {
            this.addLike(id, userName);
            this.incrementLikes(id);
            return "ok";
        }
        return "no";
    }

    private void incrementLikes(Long id) {
        Post post =postRepo.findById(id).get();
        post.setLikes(post.getLikes()+1);
        postRepo.save(post);
    }

    private void addLike(Long id, String userName) {
        Likes likes = new Likes();
        likes.setPostId(id);
        likes.setUserName(userName);
        likeRepo.save(likes);
    }

    private void incrementLikesAndDecrementDislikes(Long id) {
        Post post = this.postRepo.findById(id).get();
        post.setLikes(post.getLikes()+1);
        post.setDislikes(post.getDislikes()-1);
        this.postRepo.save(post);
    }

    @Override
    public String dislike(Long id, String userName) {
        Optional<Likes> like = this.likeRepo.getLike(id, userName);
        if (like.isPresent()) {
            this.likeRepo.delete(like.get());
            this.incrementDislikesAndIncrementLikes(id);
            this.addDislike(id, userName);
            return "ok";
        }
        if (dislikeRepo.getDislikeNumber(id, userName) == 0) {
            this.addDislike(id, userName);
            this.incrementDislike(id);
            return "ok";
        }
        return "no";
    }

    @Override
    public void addImage(MultipartFile image, Long id) throws IOException {
        if(postImageRepo.getImage(id).isPresent()){
            PostImage Image=postImageRepo.getImage(id).get();
            Image.setImageType(image.getContentType());
            Image.setImageData(image.getBytes());
            postImageRepo.save(Image);
        } else {
            PostImage Image = new PostImage(image.getContentType(), image.getBytes(), id);
            postImageRepo.save(Image);
        }
    }

    @Override
    public PostImage getImageWithPostId(Long postId) {
        return this.postImageRepo.getImage(postId).get();
    }

    @Override
    public MyPostStats getMyPostStats(String username) {
        return new MyPostStats(this.postRepo.myPostsNumber(username),
                this.postRepo.myPostsLikes(username),
                this.postRepo.myPostsDislikes(username));
    }

    @Override
    public AllPostStats getAllPostsStats() {
        return new AllPostStats(this.postRepo.AllPostsNumber(),
                this.postRepo.AllPostsLikes(),
                this.postRepo.AllPostsDislikes());
    }

    private void incrementDislike(Long id) {
        Post post =postRepo.findById(id).get();
        post.setLikes(post.getDislikes()+1);
        postRepo.save(post);
    }

    private void incrementDislikesAndIncrementLikes(Long id) {
        Post post = this.postRepo.findById(id).get();
        post.setLikes(post.getLikes()-1);
        post.setDislikes(post.getDislikes()+1);
        this.postRepo.save(post);
    }

    private void addDislike(Long id, String userName) {
        Dislikes dislikes = new Dislikes();
        dislikes.setPostId(id);
        dislikes.setUserName(userName);
        dislikeRepo.save(dislikes);
    }
}
