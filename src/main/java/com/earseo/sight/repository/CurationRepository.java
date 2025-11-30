package com.earseo.sight.repository;

import com.earseo.sight.entity.Curation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CurationRepository extends JpaRepository<Curation, Long> {
    @Query(value = "SELECT * FROM curation ORDER BY RANDOM() LIMIT :curationListSize",
            nativeQuery = true)
    List<Curation> findRandomCurations(
            @Param("curationListSize") Integer curationListSize
    );
}
