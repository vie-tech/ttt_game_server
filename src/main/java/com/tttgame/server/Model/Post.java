package com.tttgame.server.Model;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "posts")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Users owner;

    private int likes;
    private int dislikes;

    public Post(String text, Users owner) {
        this.text = text;
        this.owner = owner;
    }
}
