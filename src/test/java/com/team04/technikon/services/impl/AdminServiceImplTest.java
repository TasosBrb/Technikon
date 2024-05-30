package com.team04.technikon.services.impl;

import com.team04.technikon.model.Repair;
import com.team04.technikon.repository.PropertyOwnerRepository;
import com.team04.technikon.repository.PropertyRepository;
import com.team04.technikon.repository.RepairRepository;
import com.team04.technikon.services.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

class AdminServiceImplTest {


    private AdminService adminService;
    private PropertyOwnerRepository propertyOwnerRepository;
    private PropertyRepository propertyRepository;
    private RepairRepository repairRepository;

    @BeforeEach
    void setUp() {
        propertyOwnerRepository = mock(PropertyOwnerRepository.class);
        propertyRepository = mock(PropertyRepository.class);
        repairRepository = mock(RepairRepository.class);
        adminService =
                new AdminServiceImpl(propertyOwnerRepository, propertyRepository, repairRepository);
    }



    @Test
    void proposeCostsShouldProposeTheCostOfARepair() {
        Repair repair = new Repair();
        repair.setId(1);
        double cost = 100;
        adminService.proposeCosts(repair, cost);
        verify(repairRepository, times(1)).updateCost(repair.getId(), cost);

    }

}


