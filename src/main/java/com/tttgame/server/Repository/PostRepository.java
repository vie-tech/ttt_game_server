package com.tttgame.server.Repository;

import com.tttgame.server.Model.Post;
import com.tttgame.server.Model.Users;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
   Optional <List<Post>> findByOwner(Users user);


}
