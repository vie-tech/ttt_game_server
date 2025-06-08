package com.tttgame.server.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "messages")
@Data // Generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor
@AllArgsConstructor
public class Messages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private Users sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private Users receiver;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timeSent;
}
