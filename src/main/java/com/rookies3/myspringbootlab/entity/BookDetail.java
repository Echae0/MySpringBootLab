package com.rookies3.myspringbootlab.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.awt.print.Book;

@Entity
@Table(name = "book_details")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@DynamicUpdate
public class BookDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_detail_id")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "language")
    private String language;

    @Column(name = "page_count")
    private Integer pageCount;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "cover_image_url")
    private String coverImageUrl;

    @Column(name = "edition")
    private String edition;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", unique = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Book book;

    public void setBook(Book book) {
        this.book = book;
        if (book != null && book.getBookDetail() != this) {
            book.setBookDetail(this);
        }
    }
}
