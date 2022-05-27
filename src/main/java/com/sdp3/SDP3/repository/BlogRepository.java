package com.sdp3.SDP3.repository;

import com.sdp3.SDP3.entites.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog,Long> {
    @Query(value = "SELECT * FROM blog  WHERE store_id = ?1",nativeQuery = true)
    List<Blog> findBlogByStoreId(Long storeId);
}
