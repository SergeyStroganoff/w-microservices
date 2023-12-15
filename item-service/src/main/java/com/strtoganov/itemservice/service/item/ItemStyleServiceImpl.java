package com.strtoganov.itemservice.service.item;

import com.strtoganov.itemservice.domain.model.item.ItemStyle;
import com.strtoganov.itemservice.repository.ItemStyleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ItemStyleServiceImpl implements ItemStyleService {

    @Autowired
    ItemStyleRepository itemStyleRepository;

    @Override
    public Optional<ItemStyle> findItemStyleByStyleArticleAndStyleName(String article, String name) {
        return itemStyleRepository.findItemStyleByStyleArticleAndStyleName(article, name);
    }
}
