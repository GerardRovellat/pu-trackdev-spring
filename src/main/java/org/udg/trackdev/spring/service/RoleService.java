package org.udg.trackdev.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.udg.trackdev.spring.configuration.UserType;
import org.udg.trackdev.spring.controller.exceptions.ServiceException;
import org.udg.trackdev.spring.entity.Role;
import org.udg.trackdev.spring.repository.RoleRepository;
import org.udg.trackdev.spring.utils.ErrorConstants;

import javax.transaction.Transactional;
import java.util.List;

/**
 * The type Role service.
 */
@Service
public class RoleService {

    /**
     * The Role repository.
     */
    @Autowired
    RoleRepository roleRepository;

    /**
     * Get role.
     *
     * @param type the type
     * @return the role
     */
    @Transactional
    public Role get(UserType type) {
        List<Role> roles = roleRepository.findByUserType(type);
        Role role = null;
        if(roles.size() == 1) {
            role = roles.get(0);
        } else if (roles.size() == 0) {
            role = new Role(type);
            roleRepository.save(role);
        } else {
            throw new ServiceException(ErrorConstants.UNKNOWN_ROLE);
        }
        return role;
    }
}
