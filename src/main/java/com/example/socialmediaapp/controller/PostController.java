package com.example.socialmediaapp.controller;

import com.example.socialmediaapp.entities.Post;
import com.example.socialmediaapp.service.Impl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/post")
public class PostController {

    @Autowired
    private PostServiceImpl postService;

    @GetMapping("/")
    public ResponseEntity<Object> getAllPosts(){
        List<Post> postList = postService.getAllPosts();
        if(postList.size() == 0){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("There are no posts");
        }
        return ResponseEntity.status(HttpStatus.OK).body(postList);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<String> addPost(@RequestBody Post post, @PathVariable("userId") int userId){
        Post new_post = postService.addPost(post, userId);
        if(new_post == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Object is empty");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Post added successfully");
    }

    @PutMapping("{postId}/like/{userId}")
    public ResponseEntity<String> likePost(@PathVariable int postId, @PathVariable int userId){
        String message = postService.likePost(postId, userId);
        if(message.equals("Post null")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post with id = " + postId + " doesn't exist");
        }
        if(message.equals("User null")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id = " + userId + " doesn't exist");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Post liked successfully");
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable int postId){
        String message = postService.deletePost(postId);
        if(message.equals("Fail")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post with id = " + postId + " doesn't exist");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Post deleted successfully");
    }
}
