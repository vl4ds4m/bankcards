package com.example.bankcards.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "cards")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private Long number;

    private LocalDate expiration;

    private Boolean blocked;

    private Long balance;

    public Card(long number, User user, LocalDate expiration) {
        this.number = number;
        this.user = user;
        this.expiration = expiration;
        this.blocked = false;
        this.balance = 0L;
    }

}
