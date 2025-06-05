package net.project.ems.controller;

import net.project.ems.dto.CashbookDto;
import net.project.ems.dto.CreditDTO;
import net.project.ems.dto.DebitDTO;
import net.project.ems.entity.Cashbook;
import net.project.ems.entity.Credit;
import net.project.ems.entity.Debit;
import net.project.ems.entity.User;
import net.project.ems.repository.CreditRepository;
import net.project.ems.repository.DebitRepository;
import net.project.ems.service.CashbookService;
import net.project.ems.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/users")
public class CashbookController {


    @Autowired
    private CashbookService cashbookService;

    @Autowired
    private UserService userService;

    @Autowired
    private CreditRepository creditRepository;

    @Autowired
    private DebitRepository debitRepository;

    // Constructor for dependency injection (optional)
    @Autowired
    public CashbookController(CashbookService cashbookService, UserService userService) {
        this.cashbookService = cashbookService;
        this.userService = userService;
        this.creditRepository = creditRepository;
        this.debitRepository = debitRepository;
    }

    /**
     * Create a new Cashbook for a User.
     */
    @PostMapping("/{userId}/cashbooks")
    public ResponseEntity<?> createCashbook(@PathVariable Long userId, @RequestBody CashbookDto cashbookDto) {
        if (cashbookDto.getName() == null || cashbookDto.getName().isEmpty()) {
            return ResponseEntity.badRequest().body("Cashbook name is required.");
        }

        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        Cashbook cashbook = new Cashbook();
        cashbook.setName(cashbookDto.getName());
        cashbook.setUser(user.get());

        Cashbook createdCashbook = cashbookService.saveCashbook(cashbook);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCashbook);
    }

    /**
     * Add a Credit to a Cashbook.
     */
    @PostMapping("/cashbooks/{cashbookId}/credit")
    public ResponseEntity<?> addCredit(@PathVariable Long cashbookId, @RequestBody CreditDTO creditDTO) {
        if (creditDTO.getTitle() == null || creditDTO.getTitle().isEmpty()) {
            return ResponseEntity.badRequest().body("Credit title is required.");
        }
        if (creditDTO.getAmount() == null || creditDTO.getAmount() <= 0) {
            return ResponseEntity.badRequest().body("Credit amount must be greater than zero.");
        }

        Optional<Cashbook> cashbook = Optional.ofNullable(cashbookService.getCashbookById(cashbookId));
        if (cashbook.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cashbook not found.");
        }

        Credit credit = new Credit();
        credit.setTitle(creditDTO.getTitle());
        credit.setAmount(creditDTO.getAmount());
        credit.setCashbook(cashbook.get());

        Credit savedCredit = creditRepository.save(credit);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCredit);
    }

    /**
     * Add a Debit to a Cashbook.
     */
    @PostMapping("/cashbooks/{cashbookId}/debit")
    public ResponseEntity<?> addDebit(@PathVariable Long cashbookId, @RequestBody DebitDTO debitDTO) {
        if (debitDTO.getTitle() == null || debitDTO.getTitle().isEmpty()) {
            return ResponseEntity.badRequest().body("Debit title is required.");
        }
        if (debitDTO.getAmount() == null || debitDTO.getAmount() <= 0) {
            return ResponseEntity.badRequest().body("Debit amount must be greater than zero.");
        }

        Optional<Cashbook> cashbook = Optional.ofNullable(cashbookService.getCashbookById(cashbookId));
        if (cashbook.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cashbook not found.");
        }

        Debit debit = new Debit();
        debit.setTitle(debitDTO.getTitle());
        debit.setAmount(debitDTO.getAmount());
        debit.setCashbook(cashbook.get());

        Debit savedDebit = debitRepository.save(debit);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDebit);
    }

    /**
     * Get all Cashbooks for a User.
     */
    @GetMapping("/{userId}/cashbooks")
    public ResponseEntity<?> getCashbooksByUserId(@PathVariable Long userId) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        List<Cashbook> cashbooks = cashbookService.getAllCashbooksForUser(userId);
        return ResponseEntity.ok(cashbooks);
    }

    /**
     * Get a Cashbook by ID.
     */
    @GetMapping("/{userId}/cashbooks/{cashbookId}")
    public ResponseEntity<CashbookDto> getCashbookById(@PathVariable Long userId, @PathVariable Long cashbookId) {
        Cashbook cashbook = cashbookService.getCashbookById(cashbookId);
        return ResponseEntity.ok(new CashbookDto(cashbook));
    }


    /**
     * Delete a Cashbook by ID.
     */
    @DeleteMapping("/{userId}/cashbooks/{cashbookId}")
    public ResponseEntity<?> deleteCashbook(
            @PathVariable Long userId,
            @PathVariable Long cashbookId
    ) {
        // Call service to delete cashbook
        cashbookService.deleteCashbook(userId, cashbookId);
        return ResponseEntity.noContent().build();
    }
    /**
     * Update a Cashbook.
     */
    @PutMapping("/{userId}/cashbooks/{cashbookId}")
    public ResponseEntity<?> updateCashbook(
            @PathVariable Long userId,
            @PathVariable Long cashbookId,
            @RequestBody CashbookDto cashbookDto
    ) {
        Optional<Cashbook> cashbookOptional = Optional.ofNullable(cashbookService.getCashbookById(cashbookId));
        if (cashbookOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cashbook not found.");
        }

        Cashbook cashbook = cashbookOptional.get();
        if (!cashbook.getUser().getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Cashbook does not belong to the user.");
        }

        if (cashbookDto.getName() != null && !cashbookDto.getName().isEmpty()) {
            cashbook.setName(cashbookDto.getName());
        } else {
            return ResponseEntity.badRequest().body("Cashbook name is required.");
        }

        Cashbook updatedCashbook = cashbookService.saveCashbook(cashbook);
        return ResponseEntity.ok(updatedCashbook);
    }

    /**
     * Update a Credit in a Cashbook.
     */
    @PutMapping("/cashbooks/{cashbookId}/credit/{creditId}")
    public ResponseEntity<?> updateCredit(
            @PathVariable Long cashbookId,
            @PathVariable Long creditId,
            @RequestBody CreditDTO creditDTO
    ) {
        Optional<Credit> creditOptional = creditRepository.findById(creditId);
        if (creditOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Credit not found.");
        }

        Credit credit = creditOptional.get();
        if (!credit.getCashbook().getId().equals(cashbookId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Credit does not belong to the cashbook.");
        }

        if (creditDTO.getTitle() != null && !creditDTO.getTitle().isEmpty()) {
            credit.setTitle(creditDTO.getTitle());
        }
        if (creditDTO.getAmount() != null && creditDTO.getAmount() > 0) {
            credit.setAmount(creditDTO.getAmount());
        } else {
            return ResponseEntity.badRequest().body("Credit amount must be greater than zero.");
        }

        Credit updatedCredit = creditRepository.save(credit);
        return ResponseEntity.ok(updatedCredit);
    }

    /**
     * Update a Debit in a Cashbook.
     */
    @PutMapping("/cashbooks/{cashbookId}/debit/{debitId}")
    public ResponseEntity<?> updateDebit(
            @PathVariable Long cashbookId,
            @PathVariable Long debitId,
            @RequestBody DebitDTO debitDTO
    ) {
        Optional<Debit> debitOptional = debitRepository.findById(debitId);
        if (debitOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Debit not found.");
        }

        Debit debit = debitOptional.get();
        if (!debit.getCashbook().getId().equals(cashbookId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Debit does not belong to the cashbook.");
        }

        if (debitDTO.getTitle() != null && !debitDTO.getTitle().isEmpty()) {
            debit.setTitle(debitDTO.getTitle());
        }
        if (debitDTO.getAmount() != null && debitDTO.getAmount() > 0) {
            debit.setAmount(debitDTO.getAmount());
        } else {
            return ResponseEntity.badRequest().body("Debit amount must be greater than zero.");
        }

        Debit updatedDebit = debitRepository.save(debit);
        return ResponseEntity.ok(updatedDebit);
    }

}