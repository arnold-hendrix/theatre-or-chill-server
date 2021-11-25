// UserRepository allows User persistence to be managed by Spring by extending
// JpaRepository.

package com.arnold.mas.theatreorchill.repos;

import com.arnold.mas.theatreorchill.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    void deleteUserById(Long id);
}
