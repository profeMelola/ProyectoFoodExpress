package es.daw.librarydatarest.repository;

import es.daw.librarydatarest.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "authors")
public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findByFullNameContainingIgnoreCase(@Param("q") String q);
}
