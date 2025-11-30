package com.earseo.sight.repository;

import com.earseo.sight.dto.projection.SightDetailItemDto;
import com.earseo.sight.dto.projection.SightMapItemDto;
import com.earseo.sight.entity.Sight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SightRepository extends JpaRepository<Sight, Long> {

    @Query(value = """
        SELECT s.content_id, s.title, s.map_x, s.map_y
        FROM sight s
        WHERE ST_Intersects(
            s.geom,
            ST_MakeEnvelope(:minLongitude, :minLatitude, :maxLongitude, :maxLatitude, 4326)
        )
        """, nativeQuery = true)
    List<SightMapItemDto> findByRectangle(
            @Param("minLongitude") Double minLongitude,
            @Param("minLatitude") Double minLatitude,
            @Param("maxLongitude") Double maxLongitude,
            @Param("maxLatitude") Double maxLatitude
    );

    @Query(value = """
            SELECT s.content_id, s.title, s.map_x, s.map_y
            FROM sight s
            WHERE ST_DWithin(
                s.geom::geography,
                ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)::geography,
                :meters
            )
            """, nativeQuery = true)
    List<SightMapItemDto> findByRadius(
            @Param("meters") Double meters,
            @Param("longitude") Double longitude,
            @Param("latitude") Double latitude
    );

    @Query(value = """
                SELECT s.content_id, s.cat1, s.outl, s.title, s.addr1, s.addr3, s.map_x, s.map_y,
                s.tel, s.origin_img_url, s.use_time, s.rest_date, s.parking, s.use_fee,
                ST_Distance(
                    s.geom::geography,
                    ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)::geography
                ) as distance,
                d.docent_url
                FROM sight s
                LEFT JOIN docent d ON d.content_id = s.content_id
                WHERE s.content_id = :contentId
            """, nativeQuery = true)
    SightDetailItemDto findByContentId(
            @Param("contentId") String contentId,
            @Param("longitude") Double longitude,
            @Param("latitude") Double latitude
    );
}
