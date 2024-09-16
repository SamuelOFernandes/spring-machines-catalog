package com.product_project.machines_catalog.repository;


import com.product_project.machines_catalog.models.Machine;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MachineRepository extends JpaRepository<Machine, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Machine> findById(long l);

    List<Machine> findAll();

    List<Machine> findByIsActive(boolean isActive);

    List<Machine> findByHasPrinter(boolean hasPrinter);

    List<Machine> findByHasPrinterAndIsActive(boolean hasPrinter, boolean isActive);

    List<Machine> findByModelContainingIgnoreCase(String model);

}