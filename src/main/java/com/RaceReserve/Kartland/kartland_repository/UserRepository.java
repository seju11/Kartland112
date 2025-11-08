package com.RaceReserve.Kartland.kartland_repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.RaceReserve.Kartland.kartland_entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username); // ✅ use Optional for null safety

    Optional<User> findByEmail(String email); // ✅ consistent

    Optional<User> findByUsernameAndPassword(String username, String password); // ✅ fixed typo and Spring naming
}
