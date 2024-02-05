package com.lcwd.user.service.UserService.repositories;
import com.lcwd.user.service.UserService.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRespository  extends JpaRepository<User,String> {

}
