package com.example.bankcards.service;

import com.example.bankcards.CardTestUtils;
import com.example.bankcards.entity.Card;
import com.example.bankcards.exception.InvalidRequestException;
import com.example.bankcards.repository.CardRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CardServiceTest {

    @Test
    void testChangeCardBalance() {
        // Arrange
        Card card = CardTestUtils.createDefault();
        long addend = 74L;
        card.setBalance(card.getBalance() + addend);

        CardRepository cardRepository = mock();
        mockFind(cardRepository, card.getId(), CardTestUtils.createDefault());

        var sut = new CardService(cardRepository, mock());

        // Act
        long sum = sut.changeCardBalance(card.getId(), addend);

        // Assert
        assertEquals(card.getBalance(), sum);
        verify(cardRepository).save(card);
    }

    @Test
    void testTransfer() {
        // Arrange
        Card sender = CardTestUtils.createDefault();
        Card receiver = CardTestUtils.createAnother();
        long amount = 843L;
        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        CardRepository cardRepository = mock();
        mockFind(cardRepository, sender.getId(), CardTestUtils.createDefault());
        mockFind(cardRepository, receiver.getId(), CardTestUtils.createAnother());

        var sut = new CardService(cardRepository, mock());

        // Act
        sut.transfer(sender.getId(), receiver.getId(), amount);

        // Assert
        verify(cardRepository).save(sender);
        verify(cardRepository).save(receiver);
    }

    @Test
    void testFailedTransfer() {
        // Arrange
        Card sender = CardTestUtils.createDefault();
        Card receiver = CardTestUtils.createAnother();
        long amount = 100_000L;

        CardRepository cardRepository = mock();
        mockFind(cardRepository, sender.getId(), CardTestUtils.createDefault());
        mockFind(cardRepository, receiver.getId(), CardTestUtils.createAnother());

        var sut = new CardService(cardRepository, mock());

        // Act & Assert
        assertThrows(InvalidRequestException.class,
                () -> sut.transfer(sender.getId(), receiver.getId(), amount));
    }

    private static void mockFind(CardRepository repository, long cardId, Card card) {
        when(repository.findById(cardId)).thenReturn(Optional.of(card));
    }

}
