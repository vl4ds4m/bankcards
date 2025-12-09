package com.example.bankcards.controller;

import com.example.bankcards.openapi.api.TransferApi;
import com.example.bankcards.openapi.model.TransferRequest;
import com.example.bankcards.security.CurrentUserProvider;
import com.example.bankcards.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TransferController implements TransferApi {

    private final CardService cardService;

    private final CurrentUserProvider currentUserProvider;

    @Override
    public ResponseEntity<Void> transfer(TransferRequest transferRequest) {
        cardService.transfer(
                currentUserProvider.id(),
                transferRequest.getSenderCardId(),
                transferRequest.getReceiverCardId(),
                transferRequest.getAmount());
        return ResponseEntity.ok().build();
    }

}
