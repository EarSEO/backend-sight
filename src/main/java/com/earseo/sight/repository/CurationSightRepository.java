package com.earseo.sight.repository;

import com.earseo.sight.entity.CurationSight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CurationSightRepository extends JpaRepository<CurationSight, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM CurationSight cs WHERE cs.curationId = :curationId")
    void deleteByCurationId(@Param("curationId") Long curationId);
}
