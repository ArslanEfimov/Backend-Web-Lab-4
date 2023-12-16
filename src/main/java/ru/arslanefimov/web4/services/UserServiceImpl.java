package ru.arslanefimov.web4.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.arslanefimov.web4.model.Role;
import ru.arslanefimov.web4.model.UserEntity;
import ru.arslanefimov.web4.repositories.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService){
        this.userRepository = userRepository;
        this.roleService = roleService;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));

        return new User(user.getUserName(), user.getPassword(), mapToList(user.getRolesList()));
    }

    private Collection<GrantedAuthority> mapToList(List<Role> roles){
        Collection<GrantedAuthority> rolesCollection = roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
        return rolesCollection;
    }

    @Transactional
    @Override
    public boolean findByLogin(String userName){
        try {
            userRepository.findByUserName(userName).get();
        }catch (NoSuchElementException ex){
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public UserEntity getUserByLogin(String userName){
        return userRepository.findByUserName(userName).get();
    }
    @Transactional
    public boolean findByLoginAndPassword(String userName, String password){
        try {
            userRepository.getByUserNameAndPassword(userName, password);
        }catch (NoSuchElementException ex){
            return false;
        }
        System.out.println("wtf");
        return true;
    }

    @Transactional
    @Override
    public void addUserToDB(UserEntity user){
        Role role = new Role();
        role.setRoleName("USER");
        user.setRolesList(List.of(role));
        userRepository.save(user);
    }
}
