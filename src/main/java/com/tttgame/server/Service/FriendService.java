package com.tttgame.server.Service;


import com.tttgame.server.DTO.AcceptFriendRequestDTO;
import com.tttgame.server.DTO.FriendRequestDTO;
import com.tttgame.server.DTO.FriendRequestNotificationDTO;
import com.tttgame.server.Enums.RequestStatus;
import com.tttgame.server.Model.Friends;
import com.tttgame.server.Model.Users;
import com.tttgame.server.Repository.FriendRepository;
import com.tttgame.server.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FriendService {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FriendRepository friendRepository;

    @Autowired
    SimpMessageSendingOperations notificationTemplate;

    @Autowired
    NotificationService notificationService;


    public List<Friends> fetchAllFriends() {
        Map<String, Object> user = userService.getUserObject();
        String userUID = (String) user.get("uid");
        return friendRepository.findAllFriends(userUID);
    }

    public void isFriendRequestSaved(FriendRequestDTO request) {
        Friends friendship = new Friends();
        String userId = (String) userService.getUserObject().get("uid");
        Users user =
                userRepository.findByUid(userId).orElseThrow(() -> new IllegalArgumentException("User UID not found: " + "Friend Service"));
        Users friend =
                userRepository.findByUid(request.friendId()).orElseThrow(() -> new IllegalArgumentException("Friend UID not found" + "Friend Service"));
        boolean friendShipAlreadyExists =
                friendRepository.existsActiveFriendship(user, friend);

        if (friendShipAlreadyExists) {
            throw new IllegalStateException("Friend Request has already been " +
                    "sent");
        }
        friendship.setUser(user);
        friendship.setFriend(friend);
        friendRepository.save(friendship);

        String message = String.format("%s sent you a friend request",
                user.getUsername());

        notificationTemplate.convertAndSendToUser(
                friend.getUid(),
                "/queue/notifications",
                new FriendRequestNotificationDTO(user.getUid(),
                        user.getUsername(), friendship.getFriendshipId(),
                        friendship.getStatus(), message)
        );
    }

    public void acceptPendingFriendRequests(AcceptFriendRequestDTO acceptFriendRequestDTO) {
        String userId = (String) userService.getUserObject().get("uid");
        String username = (String) userService.getUserObject().get("user");
        int rowsUpdated =
                friendRepository.acceptFriendRequest(acceptFriendRequestDTO.friendshipId(), userId);
        if (rowsUpdated == 0) {
            throw new IllegalStateException("Could not accept friend request");
        }
        String message = String.format("%s accepted your friend request",
                username);
        notificationTemplate.convertAndSendToUser(
                acceptFriendRequestDTO.senderId(),
                "/queue/notifications",
                new FriendRequestNotificationDTO(userId, username,
                        acceptFriendRequestDTO.friendshipId(),
                        RequestStatus.ACCEPTED, message)
        );
    }

    public void declinePendingFriendRequest(String requestSenderUid) {
        String userId = (String) userService.getUserObject().get("uid");
        String username = (String) userService.getUserObject().get("user");
        Users friend =
                userRepository.findByUid(requestSenderUid).orElseThrow(() -> new UsernameNotFoundException("Could not find this friend"));
        int rowsUpdated = friendRepository.declineFriendRequest(userId,
                friend.getUid());
        if (rowsUpdated == 0) {
            throw new IllegalStateException("Could not decline friend " +
                    "request, try again");
        }
        String message = String.format("%s declined your friend request",
                username);
        notificationService.sendNotificationToUser(requestSenderUid, Map.of(
                "message", message));


    }

    public void deleteFriendShip(String friendId) {
        String userId = (String) userService.getUserObject().get("uid");
        String username = (String) userService.getUserObject().get("user");
        Users friend =
                userRepository.findByUid(friendId).orElseThrow(() -> new UsernameNotFoundException("Could not find this friend"));
        int rowsUpdated = friendRepository.deleteFriendshipBetween(userId,
                friendId);
        if (rowsUpdated == 0) {
            throw new IllegalStateException("Could not unfollow this user");
        }

        String message = String.format("%s just unfollowed you", username);
        notificationService.sendNotificationToUser(friend.getUid(), Map.of(
                "message", message));

    }
}
