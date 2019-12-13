package com.fudan.xk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fudan.xk.model.User;

@Repository
public interface UserRpository extends JpaRepository<User, String>{

	User findByUsername(String username);

}
