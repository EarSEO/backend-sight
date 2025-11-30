package com.earseo.sight.service;

import com.earseo.sight.dto.response.BookmarkList;
import com.earseo.sight.dto.response.BookmarkResponse;
import com.earseo.sight.dto.response.BookmarkStatusResponse;
import com.earseo.sight.entity.SightBookmark;
import com.earseo.sight.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;

    @Transactional
    public BookmarkStatusResponse addBookmark(Long memberId, String sightId) {
        bookmarkRepository.insertBookmark(memberId, sightId);
        return new BookmarkStatusResponse(true, sightId);
    }

    @Transactional
    public BookmarkStatusResponse deleteBookmark(Long memberId, String sightId) {

        bookmarkRepository.deleteByMemberIdAndContentId(memberId, sightId);

        return new BookmarkStatusResponse(false, sightId);
    }

    @Transactional(readOnly = true)
    public BookmarkList getBookmarkList(Long memberId) {
        List<SightBookmark> bookmarks = bookmarkRepository.findAllByMemberId(memberId);
        List<BookmarkResponse> bookmarkResponses = bookmarks.stream()
                .map(
                        item -> new BookmarkResponse(
                                item.getContentId(), item.getMemberId()
                        )
                )
                .toList();

        return new BookmarkList(bookmarkResponses);
    }
}
