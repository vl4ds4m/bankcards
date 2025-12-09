package com.example.bankcards;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.openapi.model.UserRole;
import com.example.bankcards.util.CardUtils;

public class CardTestUtils {

    private CardTestUtils() {
        throw new AssertionError();
    }

    public static Card createDefault() {
        var card = new Card();
        card.setId(123L);
        card.setNumber(3726_4923_8434_6345L);
        card.setUser(new User("Abcdef", "sup3r_s3cr31", UserRole.USER));
        card.getUser().setId(75L);
        card.setExpiration(CardUtils.getExpiration());
        card.setBlocked(false);
        card.setBalance(12450L);
        return card;
    }

    public static Card createAnother() {
        Card card = createDefault();
        card.setId(567L);
        card.setNumber(8954_8349_0234_2351L);
        card.setBalance(84290L);
        return card;
    }

}
