package com.team04.technikon.repository.impl;

import com.team04.technikon.enums.PropertyType;
import com.team04.technikon.model.Property;
import com.team04.technikon.model.PropertyOwner;
import com.team04.technikon.repository.PropertyRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class PropertyRepositoryImplTest {

    private PropertyRepository propertyRepository;
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        entityManager = mock(EntityManager.class);
        propertyRepository = new PropertyRepositoryImpl(entityManager);
    }

    @Test
    void searchWhenPropertyDoesNotExistThenReturnNull() {
        when(entityManager.find(Property.class, 1)).thenReturn(null);
        assertNull(propertyRepository.search(1));
    }


    @Test
    void updateOwnerVatWhenPropertyExists() {
        Property property = new Property(1, "address", "year", PropertyType.DETACHED_HOUSE);
        PropertyOwner propertyOwner =
                new PropertyOwner(
                        1,
                        "name",
                        "surname",
                        "address",
                        "phoneNumber",
                        "email",
                        "username",
                        "password");
        property.setOwner(propertyOwner);
        when(entityManager.find(Property.class, 1)).thenReturn(property);
        propertyRepository.updateOwnerVat(1, 2);
        assertEquals(2, property.getOwner().getVat());

    }

}