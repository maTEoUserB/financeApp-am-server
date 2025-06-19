package pl.finances.finances_app.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.finances.finances_app.dto.LastTransactionsDTO;
import pl.finances.finances_app.dto.requestsAndResponsesDto.CreateTransactionDTO;
import pl.finances.finances_app.dto.requestsAndResponsesDto.TransactionDTO;
import pl.finances.finances_app.services.TransactionService;

import java.time.LocalDate;
import java.util.List;

/**
 * REST controller for managing transactions.
 * Provides endpoints for CRUD operations and filtering transactions.
 */
@RestController
public class TransactionController {
    private final TransactionService transactionService;

    /**
     * Constructs a new TransactionController with the required dependencies.
     *
     * @param transactionService the service for transaction operations
     */
    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * Create new transaction.
     *
     * @param createDto with id of category and limit amount
     * @return a TransactionDTO object
     */
    @PostMapping("/new/transaction")
    ResponseEntity<TransactionDTO> createTransaction(@RequestBody @Valid CreateTransactionDTO createDto) {
        return transactionService.createNewTransaction(createDto);
    }

    /**
     * Get all transactions.
     *
     * @return a list of the LastTransactionsDTO
     */
    @GetMapping("/transactions")
    ResponseEntity<List<LastTransactionsDTO>> getTransactions() {
        return transactionService.getAllTransactions();
    }

    /**
     * Retrieves a filtered list of transactions based on optional query parameters.
     * Filters can include transaction type, category list, amount range, and date range.
     *
     * @param type the type of transaction (e.g., "INCOME", "EXPENSE") [optional]
     * @param categories a list of category names to filter by [optional]
     * @param startAmount the minimum transaction amount to include [optional]
     * @param endAmount the maximum transaction amount to include [optional]
     * @param startDate the earliest transaction date to include (ISO format: yyyy-MM-dd) [optional]
     * @param endDate the latest transaction date to include (ISO format: yyyy-MM-dd) [optional]
     * @return a list of transactions matching the provided filters wrapped in a ResponseEntity
     */
    @GetMapping("/transactions/filter")
    ResponseEntity<List<LastTransactionsDTO>> filterAndGetTransactions(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) List<String> categories,
            @RequestParam(required = false) Double startAmount,
            @RequestParam(required = false) Double endAmount,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ){
        return transactionService.filterAndGetTransactions(type, categories, startAmount, endAmount, startDate, endDate);
    }

    /**
     * Delete an transaction.
     *
     * @param id the ID of the transaction to delete
     * @return no content ResponseEntity
     */
    @DeleteMapping("/transaction/delete/{id}")
    ResponseEntity<?> deleteTransactions(@PathVariable long id) {
        return transactionService.deleteTransaction(id);
    }

    /**
     * Add transaction from receipt.
     *
     * @param file
     * @return no content ResponseEntity
     */
    @PostMapping("/transaction/from-receipt")
    public ResponseEntity<TransactionDTO> handleReceipt(@RequestPart("file") MultipartFile file) {
        return transactionService.createFromReceipt(file);
    }
}
