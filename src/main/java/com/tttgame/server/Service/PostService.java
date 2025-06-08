package com.tttgame.server.Service;


import com.tttgame.server.DTO.PostDTO;
import com.tttgame.server.Model.Post;
import com.tttgame.server.Model.Users;
import com.tttgame.server.Repository.PostRepository;
import com.tttgame.server.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class PostService {

    @Autowired
    UserService userService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    public void savePost(PostDTO request) {
        Users user = userService.getCurrentUser();
        Post post = new Post(request.text(), user);
        postRepository.save(post);
    }

    public List<Post> fetchAllPosts() {
        Users user = userService.getCurrentUser();
        Users currentUser = userRepository.findByUsername(user.getUsername()).orElse(null);

        assert currentUser != null;
        List <Post> posts = currentUser.getPosts();
        for(Post post: posts){
            System.out.println(post.getText());
        }
        return postRepository.findByOwner(user).orElse(null);
    }
}
