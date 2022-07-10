package com.dozen.recipes.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dozen.recipes.model.entity.RcpUser;

@Repository
public interface UserDao extends CrudRepository<RcpUser, Integer> {
	
	RcpUser findByUsername(String username);
	
}