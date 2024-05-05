package org.sid.ebankingbackend.controllers;

import org.sid.ebankingbackend.entities.*;
import org.sid.ebankingbackend.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping("/my-posts")
    private List<Post> myPosts(Principal principal) {
        return this.postService.myPosts(principal.getName());
    }

    @GetMapping("/list-posts")
    private List<Post> listPosts(Principal principal) {
        return this.postService.listPosts(principal.getName());
    }

    @PostMapping("/create-post")
    private String createPost(Principal principal, @RequestBody Post post){
        return this.postService.createPost(principal.getName(), post);
    }

    @PostMapping("/add-comment")
    private String addComment(Principal principal,@RequestParam Long id, @RequestBody Comment comment){
        return this.postService.addComment(principal.getName(),id,comment);
    }

    @DeleteMapping("/delete-post")
    private String deletePost(@RequestParam Long id){
        return this.postService.deletePost(id);
    }

    @DeleteMapping("/delete-comment")
    private String deleteComment(@RequestParam Long id){
        return this.postService.deleteComment(id);
    }

    @PostMapping("/like")
    private String like(@RequestParam Long id,Principal principal){
        return this.postService.like(id,principal.getName());
    }

    @PostMapping("/dislike")
    private String dislike(@RequestParam Long id,Principal principal){
        return this.postService.dislike(id,principal.getName());
    }

    @PostMapping("/addimage/{id}")
    public void addimgae(@RequestParam ("image") MultipartFile image , @PathVariable(value = "id") Long id) throws IOException {
        this.postService.addImage(image,id);
    }

    @GetMapping("/getimage/{id}")
    public ResponseEntity<byte[]> getImgae(@PathVariable (value = "id") Long id) {
        PostImage postImage=postService.getImageWithPostId(id);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(postImage.getImageType()))
                .body(postImage.getImageData());
    }

    @GetMapping("/my-post-stats")
    private MyPostStats myPostStats(Principal principal){
        return this.postService.getMyPostStats(principal.getName());
    }

    @GetMapping("/all-post-stats")
    private AllPostStats allPostStats(){
        return this.postService.getAllPostsStats();
    }
}
