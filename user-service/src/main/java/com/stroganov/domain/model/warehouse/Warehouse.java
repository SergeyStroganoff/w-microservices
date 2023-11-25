package com.stroganov.domain.model.warehouse;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.stroganov.domain.model.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Warehouse implements Serializable {
    @Id
    @Column(name = "warehouse_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String warehouseName;

    @Column
    private String address;

    // @ManyToMany(fetch = FetchType.EAGER,
    //         cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    // @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    // @JoinTable(
    //         name = "user_warehouse",
    //         joinColumns = @JoinColumn(name = "warehouse_id"),
    //         inverseJoinColumns = @JoinColumn(name = "username"))
    @ToString.Exclude
    @ManyToMany(mappedBy = "warehouseList", fetch = FetchType.EAGER)
    @JsonBackReference
    private List<User> userList;
}
