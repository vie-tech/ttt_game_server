package com.tttgame.server.Repository;


import com.tttgame.server.Model.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Messages, Long> {
    @Query("SELECT m FROM Messages m WHERE (m.sender.uid = :uid1 AND m.receiver.uid = :uid2) OR (m.sender.uid = :uid2 AND m.receiver.uid = :uid1) ORDER BY m.timeSent ASC")
    List<Messages> findConversationBetweenUsers(@Param("uid1") String uid1, @Param("uid2") String uid2);
}
