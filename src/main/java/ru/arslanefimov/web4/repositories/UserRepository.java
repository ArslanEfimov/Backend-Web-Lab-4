package ru.arslanefimov.web4.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.arslanefimov.web4.model.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserName(String name);
    Optional<UserEntity> getByUserNameAndPassword(String name, String password);
}
