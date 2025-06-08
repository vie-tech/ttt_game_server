package com.tttgame.server.Repository;

import com.tttgame.server.Model.Friends;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friends, Long> {
    @Query("""
        SELECT f FROM Friends f
        WHERE (f.user.UID = :uid OR f.friend.UID = :uid)
   """)
    List<Friends> findAllFriends(@Param("uid") String uid);

  /*  @Query(
            """
            SELECT f FROM Friends f
            WHERE  (f.user
            """
    )
    List<Friends> findPendingSentFriendRequests(@Param("uid") String uid);*/
}
