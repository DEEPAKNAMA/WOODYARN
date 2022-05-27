package com.sdp3.SDP3.service;

import com.sdp3.SDP3.entites.Favourite;
import com.sdp3.SDP3.entites.Product;
import com.sdp3.SDP3.entites.Users;
import com.sdp3.SDP3.repository.FavouriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavouriteServiceImpl implements FavouriteService{

    @Autowired
    private FavouriteRepository favouriteRepository;


    @Override
    public void saveFavourite(Favourite favourite) {
        favouriteRepository.save(favourite);
    }

    @Override
    public List<Favourite> getFavProductsByUserId(Users u) {
        List<Favourite> p=favouriteRepository.FindByProByUserId(u);
        return p;
    }
}
