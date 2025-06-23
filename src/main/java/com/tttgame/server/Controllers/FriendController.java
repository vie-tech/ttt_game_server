package com.tttgame.server.Controllers;


import com.tttgame.server.DTO.AcceptFriendRequestDTO;
import com.tttgame.server.DTO.FriendDTO;
import com.tttgame.server.DTO.FriendRequestDTO;
import com.tttgame.server.DTO.ResponseEntityDTO;
import com.tttgame.server.Model.Friends;
import com.tttgame.server.Service.FriendService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/friends")
public class FriendController {

    @Autowired
    FriendService friendService;


    @GetMapping("/all")
    public ResponseEntity<?> findAllFriends() {
        List<Friends> friends = friendService.fetchAllFriends();
        List<FriendDTO> dto = friends.stream().map(FriendDTO::new).toList();
        return ResponseEntity.ok(Map.of("success", true, "status",
                HttpStatus.OK, "friends", dto));
    }

    @PostMapping("/send_request")
    public ResponseEntity<?> sendFriendRequest(@RequestBody FriendRequestDTO request){

        friendService.isFriendRequestSaved(request);
        return ResponseEntity.ok(new ResponseEntityDTO(true, "Friend Request Sent Successfully"));

    }

    @PatchMapping("/accept_request")
    public ResponseEntity<?> acceptFriendRequest(@RequestBody AcceptFriendRequestDTO acceptFriendRequestDTO){
      friendService.acceptPendingFriendRequests(acceptFriendRequestDTO);

      return ResponseEntity.ok(new ResponseEntityDTO(true, "Friend request accepted"));
    }

    @PatchMapping("decline_request")
    public ResponseEntity<?> declineFriendRequest(@RequestParam String requestSenderUid){
        friendService.declinePendingFriendRequest(requestSenderUid);
        return ResponseEntity.ok(Map.of("success", true, "message", "friend request declined"));
    }

    @PatchMapping("/unfollow")
    public ResponseEntity<?> unfollowFriend(@RequestParam String friendId){
       friendService.deleteFriendShip(friendId);
       return ResponseEntity.ok(Map.of("success", true, "message", "Unfollow action successful"));
    }
}
