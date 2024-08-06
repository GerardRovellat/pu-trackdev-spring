package org.udg.trackdev.spring.entity;

import org.springframework.security.core.GrantedAuthority;
import org.udg.trackdev.spring.configuration.UserType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * The type Role.
 */
@Entity
public class Role extends BaseEntityLong implements GrantedAuthority {

    /**
     * Instantiates a new Role.
     */
    public Role() {
  }

    /**
     * Instantiates a new Role.
     *
     * @param UserType the user type
     */
    public Role(UserType UserType) {
    this.userType = UserType;
  }

  @NotNull
  @Column(unique = true)
  private UserType userType;

  public Long getId() {
    return super.getId();
  }

    /**
     * Gets user type.
     *
     * @return the user type
     */
  public UserType getUserType() {
    return userType;
  }

  @Override
  public String getAuthority() {
    return userType.toString();
  }
}
