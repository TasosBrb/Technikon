package com.team04.technikon.services.impl;

import com.team04.technikon.enums.PropertyType;
import com.team04.technikon.enums.RepairStatus;
import com.team04.technikon.enums.RepairType;
import com.team04.technikon.exceptions.ExceptionsCodes;
import com.team04.technikon.exceptions.PropertyException;
import com.team04.technikon.model.Property;
import com.team04.technikon.model.PropertyOwner;
import com.team04.technikon.model.Repair;
import com.team04.technikon.repository.PropertyOwnerRepository;
import com.team04.technikon.repository.PropertyRepository;
import com.team04.technikon.repository.RepairRepository;
import com.team04.technikon.services.OwnerService;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import com.team04.technikon.util.JpaUtil;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OwnerServiceImpl implements OwnerService {

  private final Properties sqlCommands = new Properties();

  {
    final ClassLoader loader = getClass().getClassLoader();
    try ( InputStream config = loader.getResourceAsStream("sql.properties")) {
      sqlCommands.load(config);
    } catch (IOException e) {
      throw new IOError(e);
    }
  }
  private static final Logger logger = LogManager.getLogger(OwnerServiceImpl.class);
  private final PropertyOwnerRepository propertyOwnerRepository;
  private final PropertyRepository propertyRepository;
  private final RepairRepository repairRepository;

  public OwnerServiceImpl(PropertyOwnerRepository propertyOwnerRepository, PropertyRepository propertyRepository, RepairRepository repairRepository) {
    this.propertyOwnerRepository = propertyOwnerRepository;
    this.propertyRepository = propertyRepository;
    this.repairRepository = repairRepository;
  }

  @Override
  public PropertyOwner getOwnerFromConsole() {
    PropertyOwner propertyOwner = new PropertyOwner();
    Scanner scanner = new Scanner(System.in);
    System.out.println("Give the new owner's  details in the following form: ");
    System.out.println("VAT (Nine digits), Name, Surename, Address, phone, email, user name, password ");
    String ownerDetails = scanner.nextLine();
    String[] ownerDetailsSplit = ownerDetails.split(",");
    propertyOwner.setVat(Integer.parseInt(ownerDetailsSplit[0].trim()));
    propertyOwner.setName(ownerDetailsSplit[1].trim());
    propertyOwner.setSurname(ownerDetailsSplit[2].trim());
    propertyOwner.setAddress(ownerDetailsSplit[3].trim());
    propertyOwner.setPhoneNumber(ownerDetailsSplit[4].trim());
    propertyOwner.setEmail(ownerDetailsSplit[5]);
    propertyOwner.setUsername(ownerDetailsSplit[6]);
    propertyOwner.setPassword(ownerDetailsSplit[7]);
    return propertyOwner;
  }

  @Override
  public void registerNewOwner() {
    PropertyOwner propertyOwner = getOwnerFromConsole();
    propertyOwnerRepository.create(propertyOwner);
    registerNewProperty(propertyOwner);
    registerNewProperty(propertyOwner);
    logger.info("The new owner with VAT number {} has been registered", propertyOwner.getVat());
  }

  @Override
  public Property getPropertyFromConsole(PropertyOwner propertyOwner) {
    Property property = new Property();
    Scanner scanner = new Scanner(System.in);
    System.out.println("Give the new property's  details in the following form: ");
    System.out.println("E9 number, address, type(DETACHED_HOUSE MAISONETTE APARTMENT_BUILDING)");
    String propertyDetails = scanner.nextLine();
    String[] propertyDetailsSplit = propertyDetails.split(",");
    property.setE9(Integer.parseInt(propertyDetailsSplit[0].trim()));
    property.setAddress(propertyDetailsSplit[1].trim());
    property.setPropertyType(PropertyType.valueOf(propertyDetailsSplit[2].trim().toUpperCase()));
    property.setOwner(propertyOwner);
    return property;
  }

  @Override
  public void registerNewProperty(PropertyOwner propertyOwner) {
    Property property = getPropertyFromConsole(propertyOwner);
    try {
      isValidProperty(property);
      logger.info("The new property with E9 number {} has been registered", property.getE9());
    } catch (PropertyException e) {
      System.out.println(e.getMessage());
    }
    propertyRepository.create(property);
  }

  @Override
  public Repair getRepairFromConsole(Property property) {
    Scanner scanner = new Scanner(System.in);
    Repair repair = new Repair();
    System.out.println("Give the new repair's  details in the following form: ");
    System.out.println("repair type (PAINTING INSULATION FRAMES PLUMBING ELECTRICAL_WORK), repair description, "
            + "submission date (YYYY-MM-DD), work description, start date  (YYYY-MM-DD), end date  (YYYY-MM-DD),"
            + " cost, acceptance (True, False), repair status (PENDING DECLINED IN_PROGRESS COMPLETE), "
            + " actual start date (YYYY-MM-DD), actual end date (YYYY-MM-DD)");
    String repairDetails = scanner.nextLine();
    String[] repairDetailsSplit = repairDetails.split(",");
    repair.setProperty(property);
    repair.setRepairType(RepairType.valueOf(repairDetailsSplit[0].trim().toUpperCase()));
    repair.setRepairDescription(repairDetailsSplit[1].trim());
    repair.setSubmissionDate(LocalDate.parse(repairDetailsSplit[2]));
    repair.setWorkDescription(repairDetailsSplit[3].trim());
    repair.setStartDate(LocalDate.parse(repairDetailsSplit[4].trim()));
    repair.setEndDate(LocalDate.parse(repairDetailsSplit[5].trim()));
    repair.setCost(Double.parseDouble(repairDetailsSplit[6].trim()));
    repair.setAcceptance(Boolean.parseBoolean(repairDetailsSplit[7].trim()));
    repair.setRepairStatus(RepairStatus.valueOf(repairDetailsSplit[8].trim().toUpperCase()));
    repair.setActualStartDate(LocalDate.parse(repairDetailsSplit[9].trim()));
    repair.setActualEndDate(LocalDate.parse(repairDetailsSplit[10].trim()));
    return repair;
  }

  @Override
  public void registerRepair(Property property) {
    Repair repair = getRepairFromConsole(property);
    repairRepository.create(repair);
    repairRepository.updatePropertyId(repair.getId(), property.getId());
    logger.info("The new repair has been registered");
  }

  public void displayAllOwners() {
    List<PropertyOwner> owners = propertyOwnerRepository.readAll();
    for (PropertyOwner owner : owners) {
      System.out.println(owner.getId() + " " + owner.getName() + " " + owner.getSurname()
              + " " + owner.getAddress() + " " + owner.getPhoneNumber()
              + " " + owner.getEmail() + " " + owner.getUsername() + " " + owner.getPassword());
    }
  }

  @Override
  public void displayOwnersProperties(int ownerId) {
    TypedQuery<Tuple> query = JpaUtil.getEntityManager().createQuery(sqlCommands.getProperty("select.report.properties"), Tuple.class)
            .setParameter("ownerid", ownerId);
    List<Tuple> resultList = query.getResultList();
    resultList.forEach(tuple -> System.out.println("Property id: " + tuple.get(0) + "| Address: " + tuple.get(1) + "| E9: " + tuple.get(2) + "| Property type: " + tuple.get(3) + "| Year of construction: " + tuple.get(4)));
  }

  @Override
  public void isValidProperty(Property property) throws PropertyException {
    if (String.valueOf(property.getE9()).length() != 9) {
      throw new PropertyException(ExceptionsCodes.PROPERTY_E9_NOT_VALID);
    }
    if (property.getAddress() == null) {
      throw new PropertyException(ExceptionsCodes.MISSING_PROPERTY_ADDRESS);
    }
    if ((property.getPropertyType() != PropertyType.APARTMENT_BUILDING)
            && (property.getPropertyType() != PropertyType.DETACHED_HOUSE)
            && (property.getPropertyType() != PropertyType.MAISONETTE)) {
      throw new PropertyException(ExceptionsCodes.PROPERTY_TYPE_NOT_VALID);
    }
  }

  @Override
  public void acceptRepair(Repair repair) {
    repairRepository.updateAcceptance(repair.getId(), true);
  }

  @Override
  public void declineRepair(Repair repair) {
    repairRepository.updateAcceptance(repair.getId(), false);
  }
}
