package com.vir.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vir.model.UserInfo;

public interface UserRepository extends JpaRepository<UserInfo, Integer>{
	
	public Optional<UserInfo> findByName(String name);

}
