package com.tttgame.server.Controllers;


import com.tttgame.server.DTO.PostDTO;
import com.tttgame.server.Model.Post;
import com.tttgame.server.Service.PostService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    PostService postService;

    @PostMapping("/save")
    public ResponseEntity<?> savePost(@RequestBody PostDTO request) {
        postService.savePost(request);
        return ResponseEntity.ok(Map.of("success", true, "message", "post " +
                "saved"));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Post>> fetchPosts() {
        List<Post> posts = postService.fetchAllPosts();
        System.out.println(posts);
        System.out.println(posts);
        return ResponseEntity.ok(posts);


    }
}
