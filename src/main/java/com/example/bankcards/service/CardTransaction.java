package com.example.bankcards.service;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Контроль одновременного изменения баланса карт в разных транзакциях.
 * Работает для Postgresql.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Transactional(isolation = Isolation.REPEATABLE_READ)
public @interface CardTransaction {}
