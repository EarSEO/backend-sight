package com.earseo.sight.repository;

import com.earseo.sight.entity.Docent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocentRepository extends JpaRepository<Docent, Long> {
    Docent findByContentId(String contentId);
}
