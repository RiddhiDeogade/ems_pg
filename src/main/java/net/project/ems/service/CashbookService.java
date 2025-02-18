package net.project.ems.service;

import net.project.ems.entity.Cashbook;
import net.project.ems.repository.CashbookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CashbookService {

    @Autowired
    private CashbookRepository cashbookRepository;

    public Cashbook saveCashbook(Cashbook cashbook) {
        return cashbookRepository.save(cashbook);
    }

//    public Optional<Cashbook> getCashbookById(Long id) {
//        return cashbookRepository.findById(id);
//    }

    public Optional<Cashbook> getCashbookById(Long cashbookId) {
        return cashbookRepository.findById(cashbookId); // Assuming you have a JpaRepository
    }


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
