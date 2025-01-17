package com.bytepulse.yummy.controller;

import com.bytepulse.yummy.dto.cakes.CreateCakeDto;
import com.bytepulse.yummy.dto.cakes.UpdateCakeDto;
import com.bytepulse.yummy.model.Cake;
import com.bytepulse.yummy.service.CakeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cakes")
public class CakeController {

    private final CakeService cakeService;

    public CakeController(CakeService _cakeService) {
        this.cakeService = _cakeService;
    }

    @GetMapping
    public List<Cake> getAllCakes() {
        return cakeService.getAllCakes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cake> getCakeById(@PathVariable Long id) {
        Cake cake = cakeService.getCakeById(id);
        return ResponseEntity.ok(cake);
    }

    @PostMapping
    public ResponseEntity<Cake> createCake(@Valid @RequestBody CreateCakeDto cakeDto) {
        Cake cake = new Cake();
        cake.setName(cakeDto.getName());
        cake.setPrice(cakeDto.getPrice());
        Cake createdCake = cakeService.createCake(cake);
        return ResponseEntity.ok(createdCake);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cake> updateCake(@PathVariable Long id, @Valid @RequestBody UpdateCakeDto cakeDto) {
        Cake cakeDetails = new Cake();
        cakeDetails.setName(cakeDto.getName());
        cakeDetails.setPrice(cakeDto.getPrice());
        Cake updatedCake = cakeService.updateCake(id, cakeDetails);
        return ResponseEntity.ok(updatedCake);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCake(@PathVariable Long id) {
        cakeService.deleteCake(id);
        return ResponseEntity.noContent().build();
    }
}
