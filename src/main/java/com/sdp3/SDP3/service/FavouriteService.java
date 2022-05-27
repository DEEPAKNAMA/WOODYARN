package com.sdp3.SDP3.service;

import com.sdp3.SDP3.entites.Favourite;
import com.sdp3.SDP3.entites.Product;
import com.sdp3.SDP3.entites.Users;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FavouriteService {
    
    void saveFavourite(Favourite favourite);


    List<Favourite> getFavProductsByUserId(Users u);
}
