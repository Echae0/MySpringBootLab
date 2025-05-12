package com.rookies3.myspringbootlab.service;

import com.rookies3.myspringbootlab.controller.dto.BookDTO;
import com.rookies3.myspringbootlab.entity.Book;
import com.rookies3.myspringbootlab.entity.BookDetail;
import com.rookies3.myspringbootlab.exception.BusinessException;
import com.rookies3.myspringbootlab.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    public List<BookDTO.Response> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookDTO.Response::fromEntity)
                .toList();
    }

    public BookDTO.Response getBookById(Long id) {
        Book book = findBookById(id);
        return BookDTO.Response.fromEntity(book);
    }

    public BookDTO.Response getBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BusinessException("Book Not Found with ISBN: " + isbn, HttpStatus.NOT_FOUND));
        return BookDTO.Response.fromEntity(book);
    }

    public List<BookDTO.Response> getBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author).stream()
                .map(BookDTO.Response::fromEntity)
                .toList();
    }

    public BookDTO.Response createBook(BookDTO.Request request) {
        // ISBN 중복 검사
        if (bookRepository.findByIsbn(request.getIsbn()).isPresent()) {
            throw new BusinessException("Book with this ISBN already exists", HttpStatus.CONFLICT);
        }

        // Book + BookDetail 생성
        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .price(request.getPrice())
                .publishDate(request.getPublishDate())
                .build();

        if (request.getDetailRequest() != null) {
            BookDetail detail = BookDetail.builder()
                    .description(request.getDetailRequest().getDescription())
                    .language(request.getDetailRequest().getLanguage())
                    .pageCount(request.getDetailRequest().getPageCount())
                    .publisher(request.getDetailRequest().getPublisher())
                    .coverImageUrl(request.getDetailRequest().getCoverImageUrl())
                    .edition(request.getDetailRequest().getEdition())
                    .book(book)
                    .build();
            book.setBookDetail(detail);
        }

        Book savedBook = bookRepository.save(book);
        return BookDTO.Response.fromEntity(savedBook);
    }

    public BookDTO.Response updateBook(Long id, BookDTO.Request request) {
        Book book = findBookById(id);

        // 필드 수정
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setPrice(request.getPrice());
        book.setPublishDate(request.getPublishDate());

        if (request.getDetailRequest() != null) {
            BookDetail detail = book.getBookDetail();
            if (detail == null) {
                detail = new BookDetail();
                detail.setBook(book);
                book.setBookDetail(detail);
            }

            detail.setDescription(request.getDetailRequest().getDescription());
            detail.setLanguage(request.getDetailRequest().getLanguage());
            detail.setPageCount(request.getDetailRequest().getPageCount());
            detail.setPublisher(request.getDetailRequest().getPublisher());
            detail.setCoverImageUrl(request.getDetailRequest().getCoverImageUrl());
            detail.setEdition(request.getDetailRequest().getEdition());
        }

        return BookDTO.Response.fromEntity(book);
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BusinessException("Book Not Found with ID: " + id, HttpStatus.NOT_FOUND);
        }
        bookRepository.deleteById(id);
    }

    private Book findBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Book Not Found with ID: " + id, HttpStatus.NOT_FOUND));
    }
}