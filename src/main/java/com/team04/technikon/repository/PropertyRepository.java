package com.team04.technikon.repository;

import com.team04.technikon.enums.PropertyType;
import com.team04.technikon.model.Property;
import java.util.List;

public interface PropertyRepository extends Repository<Property> {

  Property search(int id);

  List<Property> searchByVat(int vat);

  void updateOwnerVat(int propertyId, int vat);

  void updateAddress(int id, String address);

  void updateYearOfConstruction(int id, String year);

  void updatePropertyType(int id, PropertyType propertyType);

  boolean delete(int id);

  public void updateOwnerId(int propertyId, int propertyOwnerId);

  List<Property> readAll();
}
