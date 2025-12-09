package com.example.bankcards.controller;

import com.example.bankcards.exception.AuthorizationException;
import com.example.bankcards.openapi.api.CardsApi;
import com.example.bankcards.openapi.model.*;
import com.example.bankcards.security.CurrentUserProvider;
import com.example.bankcards.service.CardService;
import com.example.bankcards.util.CardUtils;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CardController implements CardsApi {

    private final CardService cardService;

    private final CurrentUserProvider currentUserProvider;

    @Override
    public ResponseEntity<List<CardInfo>> getCards(@Nullable Long userId, @Nullable Integer page) {
        userId = userId == null ? currentUserProvider.id() : userId;
        page = page == null ? 0 : page;

        if (!currentUserProvider.admin() && currentUserProvider.id() != userId) {
            throw new AuthorizationException("Пользователь не имеет доступа к сторонним картам.");
        }

        List<CardInfo> body = cardService.getCards(userId, page)
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
        long userId = createCardRequest.getUserId();
        if (currentUserProvider.id() == userId) {
            throw new AuthorizationException("Карту можно создать только для обычного пользователя.");
        }

        long cardId = cardService.createCard(userId);
        return ResponseEntity.ok(new CreateCardResponse(cardId));
    }

    @Override
    public ResponseEntity<Void> deleteCard(Long id) {
        cardService.deleteCard(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<CardBalance> getCardBalance(Long id) {
        long balance = cardService.getCardBalance(currentUserProvider.id(), id);
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
