package org.udg.trackdev.spring.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.udg.trackdev.spring.configuration.UserType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * The type Role.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role extends BaseEntityLong implements GrantedAuthority {

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
