package com.bytepulse.yummy.service;

import com.bytepulse.yummy.model.Cake;
import com.bytepulse.yummy.repository.CakeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CakeService {

    @Autowired
    private CakeRepository cakeRepository;

    public List<Cake> getAllCakes() {
        return cakeRepository.findAll();
    }

    public Cake createCake(Cake cake) {
        return cakeRepository.save(cake);
    }
}
