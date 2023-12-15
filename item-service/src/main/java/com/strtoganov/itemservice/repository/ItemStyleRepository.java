package com.strtoganov.itemservice.repository;


import com.strtoganov.itemservice.domain.model.item.ItemStyle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemStyleRepository extends JpaRepository<ItemStyle, Integer> {
    Optional<ItemStyle> findItemStyleByStyleArticleAndStyleName(String article, String name);
}
