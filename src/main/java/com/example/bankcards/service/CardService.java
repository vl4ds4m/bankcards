package com.example.bankcards.service;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.InvalidRequestException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.util.CardUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;

    private final UserRepository userRepository;

    public List<Card> getCards(long userId, int page) {
        User user = getUser(userId);
        return cardRepository.findAllByUser(user, page);
    }

    public long createCard(long userId) {
        User user = getUser(userId);
        long number = CardUtils.generateNumber();
        LocalDate expiration = CardUtils.getExpiration();
        var card = new Card(number, user, expiration);

        card = cardRepository.save(card);
        return card.getId();
    }

    public long getCardBalance(long id) {
        Card card = getCard(id);
        return card.getBalance();
    }

    @CardTransaction
    public long changeCardBalance(long id, long addend) {
        Card card = getCard(id);
        validateCardActive(card);

        long balance = card.getBalance();
        balance = balance + addend;

        if (balance < 0L) {
            throw new InvalidRequestException("Баланс после операции должен быть неотрицательным.");
        }

        card.setBalance(balance);
        cardRepository.save(card);
        return balance;
    }

    public void deleteCard(long id) {
        Card card = getCard(id);
        cardRepository.delete(card);
    }

    public void blockCard(long id) {
        Card card = getCard(id);
        card.setBlocked(true);
        cardRepository.save(card);
    }

    public void unblockCard(long id) {
        Card card = getCard(id);
        card.setBlocked(false);
        cardRepository.save(card);
    }

    @CardTransaction
    public void transfer(long senderId, long receiverId, long amount) {
        if (amount <= 0L) {
            throw new InvalidRequestException("Сумма перевода должна быть положительной.");
        }

        Card sender = getCard(senderId);
        Card receiver = getCard(receiverId);

        validateCardActive(sender);
        validateCardActive(receiver);

        long senderBalance = sender.getBalance() - amount;
        if (senderBalance < 0L) {
            throw new InvalidRequestException("Баланс карты получателя после операции должен быть неотрицательным.");
        }

        sender.setBalance(senderBalance);
        receiver.setBalance(receiver.getBalance() + amount);

        cardRepository.save(sender);
        cardRepository.save(receiver);
    }

    private User getUser(long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new InvalidRequestException("Пользователь с id = " + id + " не существует"));
    }

    private Card getCard(long id) {
        return cardRepository.findById(id).orElseThrow(() ->
                new InvalidRequestException("Карта с id = " + id + " не существует"));
    }

    private static void validateCardActive(Card card) {
        if (card.getBlocked()) {
            throw new InvalidRequestException("Карта с id = " + card.getId() + " заблокирована.");
        }
        if (CardUtils.isExpired(card)) {
            throw new InvalidRequestException("Срок действия карты с id = " + card.getId() + " истек.");
        }
    }

}
