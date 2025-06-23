package com.tttgame.server.Repository;


import com.tttgame.server.Model.FriendRequest;
import com.tttgame.server.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
}
