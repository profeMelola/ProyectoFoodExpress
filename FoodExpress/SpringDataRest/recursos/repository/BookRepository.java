package es.daw.librarydatarest.repository;


import es.daw.librarydatarest.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(path = "books")
public interface BookRepository extends JpaRepository<Book, Long> {

    Page<Book> findByTitleContainingIgnoreCase(@Param("q") String q, Pageable pageable);

    Optional<Book> findByIsbn(@Param("isbn") String isbn);
}

