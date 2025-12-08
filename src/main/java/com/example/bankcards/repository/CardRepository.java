package com.example.bankcards.repository;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findAllByUser(User user, Pageable pageable);

    default List<Card> findAllByUser(User user, int pageNumber) {
        return findAllByUser(user, PageRequest.of(pageNumber, 10, Sort.Direction.ASC, "id"));
    }

}
