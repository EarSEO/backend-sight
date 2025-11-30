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
@Table(name = "sight_bookmark", indexes = {
        @Index(name = "idx_member_content", columnList = "member_id, content_id", unique = true),
        @Index(name = "idx_member", columnList = "member_id")
})
public class SightBookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content_id",nullable = false)
    private String contentId;

    @Column(name = "member_id",nullable = false)
    private Long memberId;
}
