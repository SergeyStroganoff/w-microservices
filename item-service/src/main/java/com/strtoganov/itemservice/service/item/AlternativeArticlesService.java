package com.strtoganov.itemservice.service.item;


import com.strtoganov.itemservice.domain.model.item.AlternativeArticles;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface AlternativeArticlesService {
    Optional<AlternativeArticles> fiendByID(String articleString);
}
