package com.strtoganov.itemservice.service.item;


import com.strtoganov.itemservice.domain.model.item.AlternativeArticles;
import com.strtoganov.itemservice.repository.AlternativeArticlesRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@Setter
public class AlternativeArticlesServiceImpl implements AlternativeArticlesService {
    @Autowired
    AlternativeArticlesRepository alternativeArticlesRepository;

    @Override
    public Optional<AlternativeArticles> fiendByID(String articleString) {
        return alternativeArticlesRepository.findById(articleString);
    }
}
