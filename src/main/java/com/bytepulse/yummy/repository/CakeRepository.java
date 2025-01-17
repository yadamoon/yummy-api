package com.bytepulse.yummy.repository;

import com.bytepulse.yummy.model.Cake;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CakeRepository extends JpaRepository<Cake, Long> {
}
