package com.earseo.sight.repository;

import com.earseo.sight.dto.projection.*;
import com.earseo.sight.entity.EnSight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnSightRepository extends JpaRepository<EnSight, Long> {

    boolean existsByContentId(String contentId);

    @Query(value = """
            SELECT s.content_id, s.title, s.map_x, s.map_y, s.theme
            FROM en_sight s
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
            SELECT s.content_id, s.title, s.map_x, s.map_y, s.theme
            FROM en_sight s
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
                SELECT s.content_id, s.theme, s.sub_theme, s.overview, s.title, s.addr1, s.addr3, s.map_x, s.map_y,
                s.tel, s.origin_img_url, s.use_time, s.rest_date, s.parking, s.use_fee,
                ST_Distance(
                    s.geom::geography,
                    ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)::geography
                ) as distance,
                d.docent_url,
                CASE WHEN sb.id IS NOT NULL THEN true ELSE false END as is_bookmarked
                FROM en_sight s
                LEFT JOIN docent d ON d.content_id = s.content_id
                LEFT JOIN sight_bookmark sb ON sb.content_id = s.content_id AND sb.member_id = :memberId
                WHERE s.content_id = :contentId
            """, nativeQuery = true)
    SightDetailItemDto findByContentId(
            @Param("contentId") String contentId,
            @Param("longitude") Double longitude,
            @Param("latitude") Double latitude,
            @Param("memberId") Long memberId
    );

    @Query(value = """
            SELECT 
            s.content_id, s.title, s.addr3, s.origin_img_url, s.map_y, s.map_x, d.docent_url, s.theme
            FROM en_sight s
            LEFT JOIN docent d ON d.content_id = s.content_id
            WHERE s.content_id IN :ids
            """, nativeQuery = true)
    List<SightMetaDto> findByContentId(@Param("ids") List<String> ids);

    @Query(value = """
            SELECT s.content_id, s.title, s.sub_theme, s.origin_img_url, s.map_x, s.map_y,
            ST_Distance(
                s.geom::geography,
                ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)::geography
            ) as distance,
            s.addr3
            FROM en_sight s
            JOIN curation_sight cs ON cs.sight_content_id = s.content_id
            WHERE cs.curation_id = :curationId
            ORDER BY cs.id
            """, nativeQuery = true)
    List<CurationSightItemDto> findByCurationId(
            @Param("curationId") Long curationId,
            @Param("longitude") Double longitude,
            @Param("latitude") Double latitude
    );

    List<EnSight> findAllByContentIdIn(List<String> sightIds);


    @Query(value = """
            SELECT s.content_id, s.title, s.sub_theme, s.addr3, s.map_x, s.map_y,
            ST_Distance(
                s.geom::geography,
                ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)::geography
            ) as distance,
            CASE WHEN :memberId IS NOT NULL AND sb.id IS NOT NULL
                 THEN true
                 ELSE false
            END as is_bookmark
            FROM en_sight s
            LEFT JOIN sight_bookmark sb
                        ON sb.content_id = s.content_id
                        AND sb.member_id = :memberId
            WHERE ST_Intersects(
                s.geom,
                ST_MakeEnvelope(:minLongitude, :minLatitude, :maxLongitude, :maxLatitude, 4326)
            ) AND (
                :keyword IS NULL OR
                :keyword = '' OR
                s.title ILIKE '%' || :keyword || '%'
            )
            ORDER BY distance
            LIMIT :limit
            """, nativeQuery = true)
    List<SearchSightItemDto> findByKeywordAndRectangle(
            @Param("keyword") String keyword,
            @Param("longitude") Double longitude,
            @Param("latitude") Double latitude,
            @Param("minLongitude") Double minLongitude,
            @Param("minLatitude") Double minLatitude,
            @Param("maxLongitude") Double maxLongitude,
            @Param("maxLatitude") Double maxLatitude,
            @Param("limit") Integer limit,
            @Param("memberId") Long memberId
    );
}
