package com.product_project.machines_catalog.models;

import com.product_project.machines_catalog.enums.PaymentCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Model cannot be null or blank")
    private String model;

    @ElementCollection(targetClass = PaymentCategory.class)
    @Enumerated(EnumType.STRING)
    @NotEmpty(message = "Payment category must contain at least one element")
    private List<PaymentCategory> paymentCategory;

    @Min(value = 1, message = "Capture rate must be positive")
    private double captureRate;

    @Column(nullable = false)
    private boolean hasPrinter = false;

    @NotNull(message = "Max payment value cannot be null")
    @Digits(integer = 8, fraction = 2, message = "Max payment value must be a valid monetary amount")
    private BigDecimal maxPaymentValue;

    @Min(value = 0, message = "Max installments must be at least 0")
    private int maxInstallmentsWithoutInterest;

    @NotNull(message = "Price cannot be null")
    @Digits(integer = 6, fraction = 2, message = "Price must have a maximum of 6 digits in the integer part")
    private BigDecimal price;

    @Column(name="is_active", nullable = false)
    private boolean isActive = false;
}


