package com.strtoganov.itemservice.repository;


import com.strtoganov.itemservice.domain.model.item.AlternativeArticles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlternativeArticlesRepository extends JpaRepository<AlternativeArticles, String> {
}
