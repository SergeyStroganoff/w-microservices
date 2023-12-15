package com.strtoganov.itemservice.repository;


import com.strtoganov.itemservice.domain.model.item.Dimension;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DimensionRepository extends JpaRepository<Dimension, Integer> {
    Optional<Dimension> findDimensionByWidthAndHeightAndDepth(String width, String height, String depth);
}
