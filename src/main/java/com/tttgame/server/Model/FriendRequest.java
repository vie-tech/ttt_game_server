package com.tttgame.server.Model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "friend_request")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendRequest {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private Users sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private Users receiver;


}
