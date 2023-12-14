package com.strtoganov.itemservice.domain.model.item;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;


@Table
@Entity
@Immutable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AlternativeArticles {

    @Id
    private String alternativeArticle;

    @ManyToOne
    @JoinColumn(name = "model_model_id")
    private Model model;
}
