package com.example.bankcards.controller;

import com.example.bankcards.CardTestUtils;
import com.example.bankcards.service.CardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CardController.class)
class CardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CardService cardService;

    @Test
    void testCreateCard() throws Exception {
        // Arrange
        when(cardService.createCard(2623L)).thenReturn(89345L);

        // Act
        ResultActions actions = mockMvc.perform(
                post("/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"userId\": 2623 }"));

        // Assert
        actions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":89345}"));
    }

    @Test
    void testGetCards() throws Exception {
        // Arrange
        when(cardService.getCards(9432L, 0)).thenReturn(
                List.of(
                        CardTestUtils.createDefault(),
                        CardTestUtils.createAnother()));

        // Act
        ResultActions actions = mockMvc.perform(
                get("/cards").queryParam("userId", "9432"));

        // Assert
        actions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"number\":\"**** **** **** 6345\"")))
                .andExpect(content().string(containsString("\"number\":\"**** **** **** 2351\"")));
    }

}
