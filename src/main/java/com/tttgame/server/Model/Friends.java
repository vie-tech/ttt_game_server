package com.tttgame.server.Model;


import com.tttgame.server.Enums.RequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "friends")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Friends {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;


    @ManyToOne
    @JoinColumn(name = "friend_id")
    private Users friend;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status = RequestStatus.PENDING;

    @Column(nullable = false, unique = true, updatable = false)
       private String friendshipId = UUID.randomUUID().toString();

    @Column(nullable = false)
       private java.time.LocalDateTime createdAt = java.time.LocalDateTime.now();
}
