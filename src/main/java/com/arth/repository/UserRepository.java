package com.arth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.arth.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

	UserEntity findByEmailAndPassword(String email, String password);

	List<UserEntity> findByFirstName(String firstName);

	List<UserEntity> findByFirstNameAndLastName(String firstName,String lastName);

	List<UserEntity> findByGender(String gender);

	UserEntity findByEmail(String email);

	//active user 
	@Query(value = "select u.* from users u, projectuser pu where u.user_id = pu.user_id and pu.project_id = :projectId and pu.assign_status = 1",nativeQuery = true)
	List<UserEntity> getUserByProjectId(Integer projectId);

	
	@Query(value = "select u.* from users u, projectuser pu where u.user_id = pu.user_id and pu.project_id = :projectId and pu.assign_status = 3",nativeQuery = true)
	List<UserEntity> getUserByProjectIdHold(Integer projectId);

	
	@Query(value = "select u.* from users u, projectuser pu where u.user_id = pu.user_id and pu.project_id = :projectId and pu.assign_status = 2",nativeQuery = true)
	List<UserEntity> getUserByProjectIdRevoke(Integer projectId);


	@Query(value="select * from users where user_id in (select user_id from projectuser where project_id in (select project_id from projectuser where user_id  = :userId))",nativeQuery = true)
	List<UserEntity> pmWiseTeam(Integer userId);
	
	
}
