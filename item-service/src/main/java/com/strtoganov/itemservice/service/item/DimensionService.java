package com.strtoganov.itemservice.service.item;

import com.strtoganov.itemservice.domain.model.item.Dimension;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public interface DimensionService {
    Optional<Dimension> findDimensionByWidthAndHeightAndDepth(String width, String height, String depth);
}
