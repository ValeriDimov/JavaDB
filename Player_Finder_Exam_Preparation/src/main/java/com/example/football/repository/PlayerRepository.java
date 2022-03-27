package com.example.football.repository;

import com.example.football.models.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    Optional<Player> findByEmail(String dtoEmail);

    @Query("select p from Player p" +
            " JOIN p.stat s" +
            " where p.birthDate between :dateAfter and :dateBefore" +
            " ORDER BY s.shooting DESC, s.passing DESC, s.endurance DESC, p.lastName ASC")
    List<Player> findAllByBirthDateBetween(LocalDate dateAfter, LocalDate dateBefore);
}
