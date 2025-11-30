package com.earseo.sight.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "curation_sight")
public class CurationSight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sight_content_id", nullable = false)
    private String sightContentId;

    @Column(name = "curation_id", nullable = false)
    private Long curationId;
}
