package com.sdp3.SDP3.repository;

import com.sdp3.SDP3.entites.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store,Long> {

    @Query(value = "SELECT * FROM store  WHERE user_id = ?1",nativeQuery = true)
    Store findByUser_id(Long userId);

    @Query(value = "SELECT * FROM store  WHERE store_id = ?1",nativeQuery = true)
    Store getStoreByStoreId(Long sid);
}
