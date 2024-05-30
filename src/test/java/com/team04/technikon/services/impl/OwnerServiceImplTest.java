package com.team04.technikon.services.impl;

import com.team04.technikon.enums.PropertyType;
import com.team04.technikon.enums.RepairStatus;
import com.team04.technikon.exceptions.PropertyException;
import com.team04.technikon.model.Property;
import com.team04.technikon.model.PropertyOwner;
import com.team04.technikon.model.Repair;
import com.team04.technikon.repository.PropertyOwnerRepository;
import com.team04.technikon.repository.PropertyRepository;
import com.team04.technikon.repository.RepairRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerServiceImplTest {

    @Mock
    private PropertyOwnerRepository mockPropertyOwnerRepository;
    @Mock
    private PropertyRepository mockPropertyRepository;
    @Mock
    private RepairRepository mockRepairRepository;

    private OwnerServiceImpl ownerServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        ownerServiceImplUnderTest = new OwnerServiceImpl(mockPropertyOwnerRepository, mockPropertyRepository,
                mockRepairRepository);
    }



    @Test
    void testDisplayAllOwners() {

        final List<PropertyOwner> propertyOwners = List.of(
                new PropertyOwner(0, "name", "surname", "address", "phoneNumber", "email", "username", "password"));
        when(mockPropertyOwnerRepository.readAll()).thenReturn(propertyOwners);


        ownerServiceImplUnderTest.displayAllOwners();


    }

    @Test
    void testDisplayAllOwnersAndPropertyOwnerRepositoryReturnsNoItems() {


        when(mockPropertyOwnerRepository.readAll()).thenReturn(Collections.emptyList());


        ownerServiceImplUnderTest.displayAllOwners();


    }

    @Test
    void testDisplayOwnersProperties() {

        ownerServiceImplUnderTest.displayOwnersProperties(0);

    }


    @Test
    void testIsValidProperty_ThrowsPropertyException() {

        final Property property = new Property(0, "address", "yearOfConstruction", PropertyType.DETACHED_HOUSE);

        assertThrows(PropertyException.class, () -> ownerServiceImplUnderTest.isValidProperty(property));
    }

    @Test
    void testAcceptRepair() {

        final Repair repair = new Repair("repairDescription", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1), 0.0,
                false, RepairStatus.PENDING);

        ownerServiceImplUnderTest.acceptRepair(repair);

        verify(mockRepairRepository).updateAcceptance(0, true);
    }

    @Test
    void testDeclineRepair() {

        final Repair repair = new Repair("repairDescription", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1), 0.0,
                false, RepairStatus.PENDING);

        ownerServiceImplUnderTest.declineRepair(repair);

        verify(mockRepairRepository).updateAcceptance(0, false);
    }
}
