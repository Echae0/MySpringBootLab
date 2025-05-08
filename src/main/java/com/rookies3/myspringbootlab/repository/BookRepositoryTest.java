package com.rookies3.myspringbootlab.repository;

import com.rookies3.myspringbootlab.entity.Book;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    @Rollback(value = false)
    void testCreateBook() {
        Book book = new Book("스프링 부트 핵심", "강감찬", "ISBN-001", 25000, LocalDate.of(2025, 5, 1));
        Book saved = bookRepository.save(book);
        assertThat(saved).isNotNull();
        assertThat(saved.getTitle()).isEqualTo("스프링 부트 핵심");
    }

    @Test
    @Rollback(value = false)
    void testUpdateBook() {
        Book book = bookRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Book Not Found"));

        book.setTitle("JPA 완전정복");
        book.setPrice(40000);

        // save 생략 가능 (JPA의 dirty checking)
        assertThat(book.getTitle()).isEqualTo("JPA 완전정복");
        assertThat(book.getPrice()).isEqualTo(40000);
    }

    @Test
    @Rollback(value = false)
    void testDeleteBook() {
        Book book = bookRepository.findById(10L)
                .orElseThrow(() -> new RuntimeException("Book Not Found"));
        bookRepository.delete(book);
    }

    @Test
    void testFindByIsbn() {
        Optional<Book> result = bookRepository.findByIsbn("ISBN-001");
        Book found = result.orElseGet(() -> new Book());
        assertThat(found.getTitle()).isNotNull();  // 존재하면 title이 null이 아님
    }

    @Test
    void testFindByAuthor() {
        Optional<Book> optionalBook = bookRepository.findById(1L);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            assertThat(book.getId()).isEqualTo(1L);
        }

        Book authorBook = bookRepository.findByAuthor("강감찬")
                .stream().findFirst()
                .orElseGet(Book::new);

        assertThat(authorBook.getIsbn()).isNotNull();
    }
}
