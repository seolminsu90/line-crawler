package com.crawler.line.api.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crawler.line.api.game.domain.Game;

public interface GameRepository extends JpaRepository<Game, Long> {

}
