package com.example.playspringboot;

import java.util.ArrayList;
import java.util.List;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface UserRepository extends JpaRepository<User, String>{
	User findById(String id);
	List<User> findByIdBetween(String start, String end);
	List<User> findByIdIn(String[] ids);
	List<User> findByIdNotIn(ArrayList<String> ids);
}