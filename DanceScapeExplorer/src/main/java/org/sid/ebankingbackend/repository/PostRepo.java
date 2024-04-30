package org.sid.ebankingbackend.repository;

import org.sid.ebankingbackend.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post,Long> {

    @Query(value = "select * from post where user_name=?1",nativeQuery = true)
    List<Post> myPosts(String userName);

    @Query(value = "select count(*) from post where user_name=?1",nativeQuery = true)
    Long myPostsNumber(String username);

    @Query(value = "select SUM(likes) from post where user_name=?1",nativeQuery = true)
    Long myPostsLikes(String username);

    @Query(value = "select SUM(dislikes) from post where user_name=?1",nativeQuery = true)
    Long myPostsDislikes(String username);

    @Query(value = "select count(*) from post",nativeQuery = true)
    Long AllPostsNumber();

    @Query(value = "select SUM(likes) from post",nativeQuery = true)
    Long AllPostsLikes();

    @Query(value = "select SUM(dislikes) from post",nativeQuery = true)
    Long AllPostsDislikes();
}
