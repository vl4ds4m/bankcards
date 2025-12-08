package com.example.bankcards.controller;

import com.example.bankcards.openapi.api.CardsApi;
import com.example.bankcards.openapi.model.*;
import com.example.bankcards.service.CardService;
import com.example.bankcards.util.CardUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CardController implements CardsApi {

    private final CardService cardService;

    @Override
    public ResponseEntity<List<CardInfo>> getCards(Long userId, Optional<Integer> page) {
        List<CardInfo> body = cardService.getCards(userId, page.orElse(0))
                .stream()
                .map(c -> new CardInfo(
                        c.getId(),
                        CardUtils.getMaskedNumber(c),
                        c.getExpiration(),
                        CardUtils.getStatus(c)))
                .toList();
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<CreateCardResponse> createCard(CreateCardRequest createCardRequest) {
        long cardId = cardService.createCard(createCardRequest.getUserId());
        var body = new CreateCardResponse(cardId);
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<Void> deleteCard(Long id) {
        cardService.deleteCard(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<CardBalance> getCardBalance(Long id) {
        long balance = cardService.getCardBalance(id);
        return ResponseEntity.ok(new CardBalance(balance));
    }

    @Override
    public ResponseEntity<CardBalance> changeCardBalance(Long id, ChangeBalanceRequest changeBalanceRequest) {
        long balance = cardService.changeCardBalance(id, changeBalanceRequest.getAddend());
        return ResponseEntity.ok(new CardBalance(balance));
    }

    @Override
    public ResponseEntity<Void> blockCard(Long id) {
        cardService.blockCard(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> unblockCard(Long id) {
        cardService.unblockCard(id);
        return ResponseEntity.ok().build();
    }

}
