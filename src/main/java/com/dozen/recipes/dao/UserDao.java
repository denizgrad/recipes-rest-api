package com.dozen.recipes.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dozen.recipes.model.entity.RcpUser;

@Repository
public interface UserDao extends CrudRepository<RcpUser, Long> {
	
	RcpUser findByUsername(String username);
	
}