package com.rookies3.myspringbootlab.repository;

import com.rookies3.myspringbootlab.entity.BookDetail;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookDetailRepository extends JpaRepository<BookDetail, Long> {

    // Book ID로 BookDetail 조회
    Optional<BookDetail> findByBookId(Long bookId);

    // BookDetail과 연관된 Book을 함께 로드
    @EntityGraph(attributePaths = "book")
    Optional<BookDetail> findByIdWithBook(Long id);

    // 출판사로 BookDetail 목록 조회
    List<BookDetail> findByPublisher(String publisher);
}
