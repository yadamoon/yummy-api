package com.bytepulse.yummy.service;

import com.bytepulse.yummy.model.Cake;
import com.bytepulse.yummy.repository.CakeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CakeService {

    private final CakeRepository cakeRepository;

    public CakeService(CakeRepository _cakeRepository) {
        this.cakeRepository = _cakeRepository;
    }

    public List<Cake> getAllCakes() {
        return cakeRepository.findAll();
    }

    public Cake getCakeById(Long id) {
        return cakeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cake not found with id: " + id));
    }

    public Cake createCake(Cake cake) {
        return cakeRepository.save(cake);
    }

    public Cake updateCake(Long id, Cake cakeDetails) {
        return cakeRepository.findById(id)
                .map(cake -> {
                    cake.setName(cakeDetails.getName());
                    cake.setPrice(cakeDetails.getPrice());
                    return cakeRepository.save(cake);
                })
                .orElseThrow(() -> new RuntimeException("Cake not found with id: " + id));
    }

    public void deleteCake(Long id) {
        if (!cakeRepository.existsById(id)) {
            throw new RuntimeException("Cake not found with id: " + id);
        }
        cakeRepository.deleteById(id);
    }
}
