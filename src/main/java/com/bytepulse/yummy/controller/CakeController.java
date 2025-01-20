package com.bytepulse.yummy.controller;

import com.bytepulse.yummy.dto.cakes.CreateCakeDto;
import com.bytepulse.yummy.dto.cakes.UpdateCakeDto;
import com.bytepulse.yummy.model.Cake;
import com.bytepulse.yummy.service.CakeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cakes")
// added swwagger
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Cake Management", description = "APIs for managing cakes")
public class CakeController {

    private final CakeService cakeService;

    public CakeController(CakeService _cakeService) {
        this.cakeService = _cakeService;
    }

    @Operation(summary = "Get all cakes", description = "Retrieve a list of all cakes")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public List<Cake> getAllCakes() {
        return cakeService.getAllCakes();
    }

    @Operation(summary = "Get a cake by ID", description = "Retrieve details of a specific cake by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved cake"),
            @ApiResponse(responseCode = "404", description = "Cake not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Cake> getCakeById(@PathVariable Long id) {
        Cake cake = cakeService.getCakeById(id);
        return ResponseEntity.ok(cake);
    }

    @Operation(summary = "Create a new cake", description = "Add a new cake to the system")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully created cake"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    @PostMapping
    public ResponseEntity<Cake> createCake(@Valid @RequestBody CreateCakeDto cakeDto) {
        Cake cake = new Cake();
        cake.setName(cakeDto.getName());
        cake.setPrice(cakeDto.getPrice());
        Cake createdCake = cakeService.createCake(cake);
        return ResponseEntity.ok(createdCake);
    }

    @Operation(summary = "Update a cake", description = "Update details of an existing cake by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully updated cake"),
            @ApiResponse(responseCode = "404", description = "Cake not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Cake> updateCake(@PathVariable Long id, @Valid @RequestBody UpdateCakeDto cakeDto) {
        Cake cakeDetails = new Cake();
        cakeDetails.setName(cakeDto.getName());
        cakeDetails.setPrice(cakeDto.getPrice());
        Cake updatedCake = cakeService.updateCake(id, cakeDetails);
        return ResponseEntity.ok(updatedCake);
    }

    @Operation(summary = "Delete a cake", description = "Remove a specific cake by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Successfully deleted cake"),
            @ApiResponse(responseCode = "404", description = "Cake not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCake(@PathVariable Long id) {
        cakeService.deleteCake(id);
        return ResponseEntity.noContent().build();
    }
}
