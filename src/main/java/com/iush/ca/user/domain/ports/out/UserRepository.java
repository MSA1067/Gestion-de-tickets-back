package com.iush.ca.user.domain.ports.out;

import com.iush.ca.user.domain.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByIdAndActive(Integer id , String active);

}
