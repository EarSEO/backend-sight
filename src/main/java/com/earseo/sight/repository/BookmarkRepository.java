package com.earseo.sight.repository;

import com.earseo.sight.entity.SightBookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<SightBookmark, Long> {
    @Modifying
    @Query(value = "INSERT INTO sight_bookmark (member_id, content_id) " +
            "VALUES (:memberId, :contentId) " +
            "ON CONFLICT (member_id, content_id) DO NOTHING",
            nativeQuery = true)
    void insertBookmark(
            @Param("memberId") Long memberId,
            @Param("contentId") String contentId
    );

    void deleteByMemberIdAndContentId(Long memberId, String sightId);

    List<SightBookmark> findAllByMemberId(Long memberId);
}
