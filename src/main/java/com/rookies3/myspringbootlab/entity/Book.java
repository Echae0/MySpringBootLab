import com.rookies3.myspringbootlab.entity.BookDetail;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

@Entity
@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@DynamicUpdate
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    @ToString.Include
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(unique = true, nullable = false)
    private String isbn;

    @Column(nullable = false)
    private Integer price;

    private LocalDate publishDate;

    @OneToOne(mappedBy = "book",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private BookDetail bookDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    @ToString.Exclude
    private Publisher publisher;

    public void setBookDetail(BookDetail bookDetail) {
        this.bookDetail = bookDetail;
        if (bookDetail != null) {
            bookDetail.setBook(this);
        }
    }
}
