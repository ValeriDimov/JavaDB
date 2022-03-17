package com.example.softuni_game_store.repositories;

import com.example.softuni_game_store.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
        User findUserByEmail(String email);

        User findUserByEmailAndPassword(String email, String password);
}
