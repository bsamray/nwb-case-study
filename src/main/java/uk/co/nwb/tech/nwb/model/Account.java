package uk.co.nwb.tech.nwb.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Builder
public class Account {

    @NotNull
    @NotEmpty
    private String account;

    @Positive
    private Double amount;

}
