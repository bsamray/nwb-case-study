package uk.co.nwb.tech.nwb.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.nwb.tech.nwb.model.Transaction;
import uk.co.nwb.tech.nwb.service.TransactionServiceInterface;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/v1/transactions")
public class TransactionController {

    @Resource
    private TransactionServiceInterface transactionService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> transferFunds(@Valid @RequestBody Transaction transaction) {
        transactionService.transferFunds(transaction);
        return ResponseEntity.noContent().build();
    }

}
