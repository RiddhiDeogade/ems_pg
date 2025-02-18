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


        Optional<Cashbook> findByIdAndUserId(Long cashbookId, Long userId);
    }

