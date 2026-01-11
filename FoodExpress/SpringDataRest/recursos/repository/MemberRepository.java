package es.daw.librarydatarest.repository;

import es.daw.librarydatarest.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(path = "members")
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(@Param("email") String email);
}
