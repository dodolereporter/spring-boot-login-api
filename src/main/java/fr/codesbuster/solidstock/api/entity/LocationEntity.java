package fr.codesbuster.solidstock.api.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "location")
public class LocationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    private String description;
    private String position;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @Nullable
    private LocationEntity parent;

    @OneToMany(mappedBy = "parent")
    private List<LocationEntity> childs = new ArrayList<>();

    @OneToMany(mappedBy = "location")
    private List<StockMovementEntity> stockMovements = new ArrayList<>();

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

}