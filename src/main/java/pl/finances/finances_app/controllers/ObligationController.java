package pl.finances.finances_app.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.finances.finances_app.dto.AllObligationsDTO;
import pl.finances.finances_app.dto.requestsAndResponsesDto.CreateObligationDTO;
import pl.finances.finances_app.dto.requestsAndResponsesDto.ObligationDTO;
import pl.finances.finances_app.services.ObligationService;


/**
 * REST controller for managing obligations.
 * Provides endpoints for CRUD operations (without delete) and searching obligations.
 */
@Controller
public class ObligationController {
    private final ObligationService obligationService;

    /**
     * Constructs a new ObligationController with the required dependencies.
     *
     * @param obligationService the service for obligation operations
     */
    public ObligationController(ObligationService obligationService) {
        this.obligationService = obligationService;
    }

    /**
     * Create new obligation.
     *
     * @param createDto with id of category and limit amount
     * @return a ObligationDTO object
     */
    @PostMapping("/new/obligation")
    ResponseEntity<ObligationDTO> createObligation(@RequestBody @Valid CreateObligationDTO createDto){
        return obligationService.createNewObligation(createDto);
    }

    /**
     * Update an obligation.
     *
     * @param id the ID of the obligation to update
     * @return the AllObligationsDTO
     */
    @PostMapping("/update/obligation/{id}")
    ResponseEntity<ObligationDTO> updateObligation(@PathVariable Long id) {
        return obligationService.updateObligation(id);
    }

    /**
     * Get all obligations.
     *
     * @return the AllObligationsDTO with lists of paid and unpaid obligations
     */
    @GetMapping("/obligations")
    ResponseEntity<AllObligationsDTO> getObligations(){
        return obligationService.getObligations();
    }

    /**
     * Delete an obligation.
     *
     * @param id the ID of the obligation to delete
     * @return no content ResponseEntity
     */
    @DeleteMapping("/obligation/delete/{id}")
    ResponseEntity<?> deleteObligation(@PathVariable long id) {
        return obligationService.deleteObligation(id);
    }
}
