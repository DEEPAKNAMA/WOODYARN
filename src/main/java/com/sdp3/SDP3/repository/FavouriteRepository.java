package com.sdp3.SDP3.repository;

import com.sdp3.SDP3.entites.Favourite;
import com.sdp3.SDP3.entites.Product;
import com.sdp3.SDP3.entites.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface FavouriteRepository extends JpaRepository<Favourite,Long> {

    @Query(value = "SELECT * FROM favourite  WHERE user_id = ?1",nativeQuery = true)
    List<Favourite> FindByProByUserId(Users u);

}
