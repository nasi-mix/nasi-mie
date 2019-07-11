package me.qfdk.nasimie.repository;

import me.qfdk.nasimie.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByContainerId(String containerId);
    User findByWechatName(String name);
    List<User> findUserByIconNotLike(String icon);
    Integer countByContainerLocation(String location);
}
