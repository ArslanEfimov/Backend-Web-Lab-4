package ru.arslanefimov.web4.services;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.arslanefimov.web4.model.UserEntity;

@NoRepositoryBean
public interface UserService extends UserDetailsService {

     boolean findByLogin(String userName);
    UserEntity getUserByLogin(String userName);
    void addUserToDB(UserEntity user);
}
