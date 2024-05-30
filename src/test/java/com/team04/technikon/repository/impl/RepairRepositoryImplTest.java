package com.team04.technikon.repository.impl;

import com.team04.technikon.enums.PropertyType;
import com.team04.technikon.enums.RepairStatus;
import com.team04.technikon.enums.RepairType;
import com.team04.technikon.model.Property;
import com.team04.technikon.model.Repair;
import com.team04.technikon.repository.RepairRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RepairRepositoryImplTest {

    private RepairRepository repairRepository;
    private EntityManager entityManager;
    private Query query;

    @BeforeEach
    void setUp() {
        entityManager = mock(EntityManager.class);
        query = mock(Query.class);
        repairRepository = new RepairRepositoryImpl(entityManager);
    }


    @Test
    void updateRepairDescriptionWhenIdIsGiven() {
        Repair repair =
                new Repair(
                        "Repair description",
                        LocalDate.now(),
                        LocalDate.now(),
                        100,
                        true,
                        RepairStatus.PENDING);
        repair.setId(1);
        when(entityManager.find(eq(Repair.class), eq(1))).thenReturn(repair);
        repairRepository.updateRepairDescription(1, "New repair description");
        assertEquals("New repair description", repair.getRepairDescription());
    }

    @Test
    void updateRepairTypeWhenIdIsGiven() {
        Repair repair =
                new Repair(
                        "Repair description",
                        LocalDate.now(),
                        LocalDate.now(),
                        100,
                        true,
                        RepairStatus.PENDING);
        repair.setId(1);
        when(entityManager.find(eq(Repair.class), eq(1))).thenReturn(repair);
        repairRepository.updateRepairType(1, RepairType.PAINTING);
        assertEquals(RepairType.PAINTING, repair.getRepairType());
    }



}