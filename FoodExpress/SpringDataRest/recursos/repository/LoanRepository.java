package es.daw.librarydatarest.repository;

import es.daw.librarydatarest.entity.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "loans")
public interface LoanRepository extends JpaRepository<Loan, Long> {

    Page<Loan> findByMemberId(@Param("memberId") Long memberId, Pageable pageable);

    Page<Loan> findByBookId(@Param("bookId") Long bookId, Pageable pageable);
}