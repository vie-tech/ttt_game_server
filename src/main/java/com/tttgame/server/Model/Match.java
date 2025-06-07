package com.tttgame.server.Model;


import jakarta.persistence.*;

@Table(name = "matches")
@Entity
public class Match {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player1_id", nullable = false)
    private Users player1;

    @ManyToOne
    @JoinColumn(name = "player2_id", nullable = false)
    private Users player2;

    @ManyToOne
    @JoinColumn(name = "winner_id")
    private Users winner;

    @Column(name = "status")
    private String status;

    public Users getPlayer1() {
        return player1;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPlayer1(Users player1) {
        this.player1 = player1;
    }

    public Users getPlayer2() {
        return player2;
    }

    public void setPlayer2(Users player2) {
        this.player2 = player2;
    }

    public Users getWinner() {
        return winner;
    }

    public void setWinner(Users winner) {
        this.winner = winner;
    }
}
