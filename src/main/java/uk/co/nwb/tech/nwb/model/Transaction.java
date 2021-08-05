package uk.co.nwb.tech.nwb.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Builder
public class Transaction {

    @NotNull
    @NotEmpty
    @JsonProperty("from_account")
    private String fromAccount;

    @NotNull
    @NotEmpty
    @JsonProperty("to_account")
    private String toAccount;

    @Positive
    private Double amount;

}
