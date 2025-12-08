package com.example.bankcards.util;

import com.example.bankcards.entity.Card;
import com.example.bankcards.openapi.model.CardInfo.StatusEnum;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

public class CardUtils {

    private CardUtils() {
        throw new AssertionError();
    }

    public static LocalDate getExpiration() {
        return LocalDate.now()
                .plusYears(5)
                .withDayOfMonth(1);
    }

    public static long generateNumber() {
        var random = ThreadLocalRandom.current();
        return random.nextLong(0, 1_0000_0000_0000_0000L); // TODO use more reliable approach
    }

    public static StatusEnum getStatus(Card card) {
        if (card.getBlocked()) {
            return StatusEnum.BLOCKED;
        }
        if (isExpired(card)) {
            return StatusEnum.EXPIRED;
        }
        return StatusEnum.ACTIVE;
    }

    public static boolean isExpired(Card card) {
        return card.getExpiration().isBefore(LocalDate.now());
    }

    public static String getMaskedNumber(Card card) {
        return "**** **** **** %04d".formatted(card.getNumber() % 1_0000L);
    }

}
