package com.strtoganov.itemservice.repository;


import com.strtoganov.itemservice.domain.model.item.Manufacture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManufactureRepository extends JpaRepository<Manufacture, Integer> {
    Optional<Manufacture> findByNameAndDescription(String name, String description);
}
