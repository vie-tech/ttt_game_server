package com.tttgame.server.Repository;

import com.tttgame.server.Model.Friends;
import com.tttgame.server.Model.Users;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friends, Long> {
    @Query("""
        SELECT f FROM Friends f
        WHERE (f.user.uid = :uid OR f.friend.uid = :uid)
   """)
    List<Friends> findAllFriends(@Param("uid") String uid);

    @Query("""
        SELECT COUNT(f) > 0 FROM Friends f
        WHERE (
            (f.user = :user AND f.friend = :friend)
            OR
            (f.user = :friend AND f.friend = :user)
        )
        AND f.status <> com.tttgame.server.Enums.RequestStatus.REJECTED
    """)
    boolean existsActiveFriendship(@Param("user") Users user, @Param("friend") Users friend);


    @Transactional
    @Modifying
    @Query("""
        UPDATE Friends f
        SET f.status = com.tttgame.server.Enums.RequestStatus.ACCEPTED
        WHERE f.friendshipId = :friendshipId
          AND f.friend.uid = :receiverUid
          AND f.status = com.tttgame.server.Enums.RequestStatus.PENDING
    """)
    int acceptFriendRequest(
        @Param("friendshipId") String friendshipId,
        @Param("receiverUid") String receiverUid
    );


    @Transactional
    @Modifying
    @Query("""
        DELETE FROM Friends f
        WHERE
            (f.user.uid = :uid1 AND f.friend.uid = :uid2)
            OR
            (f.user.uid = :uid2 AND f.friend.uid = :uid1)
    """)
    int deleteFriendshipBetween(
        @Param("uid1") String uid1,
        @Param("uid2") String uid2
    );

    @Transactional
    @Modifying
    @Query("""
        UPDATE Friends f
        SET f.status = com.tttgame.server.Enums.RequestStatus.REJECTED
        WHERE (
            (f.user.uid = :uid1 AND f.friend.uid = :uid2)
            OR
            (f.user.uid = :uid2 AND f.friend.uid = :uid1)
        )
        AND f.status = com.tttgame.server.Enums.RequestStatus.PENDING
    """)
    int declineFriendRequest(
        @Param("uid1") String uid1,
        @Param("uid2") String uid2
    );


}
