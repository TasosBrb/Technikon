package com.team04.technikon.services;

import com.team04.technikon.exceptions.PropertyException;
import com.team04.technikon.model.Property;
import com.team04.technikon.model.PropertyOwner;
import com.team04.technikon.model.Repair;

public interface OwnerService {


  /**
   * This method registers a new Owner that got inserted in the console.
   */
  void registerNewOwner();

  /**
   * This method registers a new Property that the owner inserted in the
   * console.
   *
   * @param propertyOwner
   */
  void registerNewProperty(PropertyOwner propertyOwner);


  /**
   * This method gets a  new Property from the console. To be used in
   * registering a new property by its owner.
   *
   * @param propertyOwner
   * @return
   */
  Property getPropertyFromConsole(PropertyOwner propertyOwner);

  /**
   * This method gets a anew Property owner from the console. To be used in
   * registering a new owner.
   *
   * @return
   */
  PropertyOwner getOwnerFromConsole();

  /**
   * This method gets a  new Repair from the console. To be used in registering
   * a new Repair.
   *
   * @param property
   * @return
   */
  Repair getRepairFromConsole(Property property);

  /**
   * This method registers a new repair that the owner inserted in the console.
   *
   * @param property
   */
  void registerRepair(Property property);
  
  void displayAllOwners();

  void displayOwnersProperties(int ownerId);

  /**
   * This method checks if the property inserted fulfills the business
   * requirements
   *
   * @param property
   * @throws PropertyException
   */
  void isValidProperty(Property property) throws PropertyException;


  /**
   * This method changes acceptance status of a repair to true
   *
   * @param repair
   */
  void acceptRepair(Repair repair);

  /**
   * This method changes acceptance status of a repair to false
   *
   * @param repair
   */
  void declineRepair(Repair repair);

}
