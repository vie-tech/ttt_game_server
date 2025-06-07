package com.tttgame.server.Model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table (name = "posts")
@Entity
@Getter
@Setter
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

    public Post(){

    }

    public Long getId() {
        return id;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Users getOwner() {
        return owner;
    }

    public void setOwner(Users owner) {
        this.owner = owner;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }
}
