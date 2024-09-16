package com.product_project.machines_catalog.dto;

import com.product_project.machines_catalog.enums.PaymentCategory;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Valid
public class MachineDTO {

    @NotBlank(message = "Model cannot be null or blank")
    private String model;

    @NotEmpty(message = "Payment category must contain at least one element")
    private List<PaymentCategory> paymentCategory;

    @Min(value = 1, message = "Capture rate must be positive")
    private double captureRate;

    private boolean hasPrinter = false;

    @NotNull(message = "Max payment value cannot be null")
    @Digits(integer = 8, fraction = 2, message = "Max payment value must be a valid monetary amount")
    private BigDecimal maxPaymentValue;

    @Min(value = 0, message = "Max installments must be at least 0")
    private int maxInstallmentsWithoutInterest;

    @NotNull(message = "Price cannot be null")
    @Digits(integer = 6, fraction = 2, message = "Price must have a maximum of 6 digits in the integer part")
    private BigDecimal price;

    private boolean isActive = false;
}
