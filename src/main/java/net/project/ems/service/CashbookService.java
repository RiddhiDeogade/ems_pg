package net.project.ems.service;

import jakarta.transaction.Transactional;
import net.project.ems.dto.CashbookDto;
import net.project.ems.entity.Cashbook;
import net.project.ems.repository.CashbookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CashbookService {

    @Autowired
    private CashbookRepository cashbookRepository;

    public Cashbook saveCashbook(Cashbook cashbook) {
        return cashbookRepository.save(cashbook);
    }
    @Autowired
    public CashbookService(CashbookRepository cashbookRepository) {
        this.cashbookRepository = cashbookRepository;
    }

    @Transactional
    public Cashbook getCashbookById(Long id) {
        return cashbookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cashbook not found with ID: " + id));
    }


    @Transactional  // Ensures the session stays open
    public List<Cashbook> getCashBooksByUserId(Long userId) {
        List<Cashbook> cashbooks = cashbookRepository.findByUserIdWithCreditsAndDebits(userId);

        // Force loading of lazy collections
        cashbooks.forEach(c -> {
            c.getCredits().size(); // Loads credits
            c.getDebits().size();  // Loads debits
        });

        return cashbooks;
    }

    @Transactional
    public void deleteCashbook(Long userId, Long cashbookId) {
        System.out.println("Attempting to delete Cashbook with ID: " + cashbookId + " for User ID: " + userId);

        Optional<Cashbook> optionalCashbook = cashbookRepository.findByIdAndUserId(cashbookId, userId);

        if (optionalCashbook.isEmpty()) {
            System.out.println("Cashbook not found for given User ID and Cashbook ID.");
        } else {
            System.out.println("Cashbook found: " + optionalCashbook.get());
        }

        Cashbook cashbook = optionalCashbook
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cashbook with ID " + cashbookId + " not found for User ID " + userId));

        cashbookRepository.delete(cashbook);
        System.out.println("Cashbook deleted successfully.");
    }

    public List<Cashbook> getAllCashbooksForUser(Long userId) {
        return cashbookRepository.findByUserId(userId);
    }




}
