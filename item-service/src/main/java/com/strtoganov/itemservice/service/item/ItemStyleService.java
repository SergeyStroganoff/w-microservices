package com.strtoganov.itemservice.service.item;

import com.strtoganov.itemservice.domain.model.item.ItemStyle;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ItemStyleService {
    Optional<ItemStyle> findItemStyleByStyleArticleAndStyleName(String article, String name);
}
