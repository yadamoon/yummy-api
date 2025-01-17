package com.bytepulse.yummy.controller;

import com.bytepulse.yummy.model.Cake;
import com.bytepulse.yummy.service.CakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cakes")
public class CakeController {

    @Autowired
    private CakeService cakeService;

    @GetMapping
    public List<Cake> getAllCakes() {
        return cakeService.getAllCakes();
    }

    @PostMapping
    public Cake createCake(@RequestBody Cake cake) {
        return cakeService.createCake(cake);
    }
}
