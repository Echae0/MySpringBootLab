package com.rookies3.myspringbootlab.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "book_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id")
    private Long id;

    @Column(length = 1000)
    private String description;

    private String language;

    private Integer pageCount;

    private String publisher;

    private String coverImageUrl;

    private String edition;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", unique = true)
    @ToString.Exclude
    private Book book;
}
