package net.project.ems.repository;

import jakarta.transaction.Transactional;
import net.project.ems.entity.Cashbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CashbookRepository extends JpaRepository<Cashbook, Long> {

    @Query("SELECT c FROM Cashbook c WHERE c.user.id = :userId")
    List<Cashbook> findByUserId(@Param("userId") Long userId);

    @Query("SELECT c FROM Cashbook c WHERE c.id = :cashbookId AND c.user.id = :userId")
    Optional<Cashbook> findByIdAndUserId(@Param("cashbookId") Long cashbookId, @Param("userId") Long userId);

    @Query("SELECT c FROM Cashbook c LEFT JOIN FETCH c.credits LEFT JOIN FETCH c.debits WHERE c.user.id = :userId")
    List<Cashbook> findByUserIdWithCreditsAndDebits(@Param("userId") Long userId);

}
