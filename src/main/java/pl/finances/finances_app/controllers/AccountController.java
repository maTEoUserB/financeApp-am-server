package pl.finances.finances_app.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.finances.finances_app.dto.IndexDTO;
import pl.finances.finances_app.dto.SummaryDTO;
import pl.finances.finances_app.dto.requestsAndResponsesDto.SaldoDTO;
import pl.finances.finances_app.services.AccountService;

/**
 * REST controller for managing users accounts.
 * Provides endpoints for operations with main and analysis information about accounts.
 */
@RestController
public class AccountController {
    private final AccountService accountService;

    /**
     * Constructs a new AccountController with the required dependencies.
     *
     * @param accountService the service for account operations
     */
    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Get main information about logged-in user account.
     *
     * @return the IndexDTO with information like balance, weekly expenses or savings balance.
     */
    @GetMapping("/index")
    ResponseEntity<IndexDTO> index() {
        return accountService.getMainAccountInformation();
    }

    /**
     * Get a summary of expenses and income.
     *
     * @return the SummaryDTO with information like average income, total expenses or biggest expense.
     */
    @GetMapping("/summary")
    ResponseEntity<SummaryDTO> getAccountSummary(){
        return accountService.getAccountSummary();
    }

    /**
     * Set saldo of user account.
     *
     * @return the SaldoDTO with amount of saldo.
     */
    @PostMapping("/saldo")
    ResponseEntity<SaldoDTO> setFirsSaldo(@RequestBody @Valid SaldoDTO saldo){
        return accountService.setFirstSaldo(saldo);
    }
}
