package ru.arslanefimov.web4.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.arslanefimov.web4.model.Role;
import ru.arslanefimov.web4.repositories.RoleRepository;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    public Role getUserRole(String roleName){
        return roleRepository.findByRoleName(roleName).get();
    }


}
