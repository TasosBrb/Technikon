package com.team04.technikon.services.impl;

import com.team04.technikon.enums.PropertyType;
import com.team04.technikon.enums.RepairStatus;
import com.team04.technikon.model.Property;
import com.team04.technikon.model.PropertyOwner;
import com.team04.technikon.model.Repair;
import com.team04.technikon.repository.PropertyOwnerRepository;
import com.team04.technikon.repository.PropertyRepository;
import com.team04.technikon.repository.RepairRepository;
import com.team04.technikon.services.IoServices;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IoServiceImpl implements IoServices {

  private static final Logger logger = LogManager.getLogger(IoServiceImpl.class);
  private static final String USER_FILE_NAME = "owners.csv";
  private PropertyOwnerRepository propertyOwnerRepository;
  private PropertyRepository propertyRepository;
  private RepairRepository repairRepository;

  public IoServiceImpl() {

  }

  public IoServiceImpl(PropertyOwnerRepository propertyOwnerRepository, PropertyRepository propertyRepository, RepairRepository repairRepository) {
    this.propertyOwnerRepository = propertyOwnerRepository;
    this.propertyRepository = propertyRepository;
    this.repairRepository = repairRepository;
  }

  @Override
  public void savePropertyOwnerToCsv(String filename) {
    File file = new File(filename);
    List<PropertyOwner> propertyOwners = propertyOwnerRepository.readAll();
    try ( PrintWriter pw = new PrintWriter(file)) {
      pw.println("id,vat,name,surname,address,phoneNumber,email,username,password,properties");
      for (PropertyOwner propertyOwner : propertyOwners) {
        pw.println(propertyOwner.getId()
                + "," + propertyOwner.getVat()
                + "," + propertyOwner.getName()
                + "," + propertyOwner.getSurname()
                + "," + propertyOwner.getAddress()
                + "," + propertyOwner.getPhoneNumber()
                + "," + propertyOwner.getEmail()
                + "," + propertyOwner.getUsername()
                + "," + propertyOwner.getPassword()
                + "," + propertyOwner.getProperties());
      }
      logger.info("Property owners saved to csv file ");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void savePropertyToCsv(String filename) {
    File file = new File(filename);
    List<Property> properties = propertyRepository.readAll(); // We will create read() method later
    try ( PrintWriter pw = new PrintWriter(file)) {
      pw.println("id,e9,address,yearOfConstruction,propertyType,owner,repairs");
      for (Property property : properties) {
        pw.println(
                property.getId()
                + "," + property.getE9()
                + "," + property.getAddress()
                + "," + property.getYearOfConstruction()
                + "," + property.getPropertyType()
                + "," + property.getOwner()
                + "," + property.getRepairs());
      }
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    logger.info("Properties saved to csv file ");
  }

  @Override
  public void saveRepair(String filename) {
    File file = new File(filename);
    List<Repair> repairs = repairRepository.readAll(); // We will create read() method later
    try ( PrintWriter pw = new PrintWriter(file)) {
      pw.println("id,owner,property,repairType,repairDescription,submissionDate,workDescription,startDate,endDate,cost,acceptance,repairStatus,actualStartDate,actualEndDate");
      for (Repair repair : repairs) {
        pw.println(
                repair.getId()
                + "," + repair.getProperty()
                + "," + repair.getRepairType()
                + "," + repair.getRepairDescription()
                + "," + repair.getSubmissionDate()
                + "," + repair.getWorkDescription()
                + "," + repair.getStartDate()
                + "," + repair.getEndDate()
                + "," + repair.getCost()
                + "," + repair.isAcceptance()
                + "," + repair.getRepairStatus()
                + "," + repair.getActualStartDate()
                + "," + repair.getActualEndDate()
        );
      }
      logger.info("Repairs saved to csv file ");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<String[]> readCsvFile(String fileName) {
    List<String[]> list = new ArrayList<>();
    BufferedReader br;
    try {
      br = new BufferedReader(new FileReader(fileName));
      String str;
      while ((str = br.readLine()) != null) {
        list.add(str.split(","));
      }
      logger.info("The csv file {} has been read ", fileName);
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
      return null;
    }
    return list;
  }

  @Override
  public List<PropertyOwner> loadOwnerData(String fileName) {
    List<String[]> list = readCsvFile(fileName);
    int vat;
    String name;
    String surname;
    String address;
    String phoneNumber;
    String email;
    String username;
    String password;
    List<PropertyOwner> propertyOwners = new ArrayList<>();
    for (String[] temp : list) {
      vat = Integer.parseInt(temp[0]);
      name = temp[1];
      surname = temp[2];
      address = temp[3];
      phoneNumber = temp[4];
      email = temp[5];
      username = temp[6];
      password = temp[7];
      propertyOwners.add(new PropertyOwner(vat, name, surname, address, phoneNumber, email, username, password));
      logger.info("The owner with VAT number {} has been loaded", vat);
    }
    return propertyOwners;
  }

  @Override
  public List<Property> loadPropertyData(String fileName) {
    List<String[]> list = readCsvFile(fileName);
    int e9;
    String address;
    String yearOfConstruction;
    PropertyType propertyType;
    List<Property> properties = new ArrayList<>();
    for (String[] temp : list) {
      e9 = Integer.parseInt(temp[0]);
      address = temp[1];
      yearOfConstruction = temp[2];
      propertyType = PropertyType.valueOf(temp[3]);
      properties.add(new Property(e9, address, yearOfConstruction, propertyType));
      logger.info("The property with E9 number {} has been loaded", e9);
    }
    return properties;
  }

  @Override
  public List<Repair> loadRepairData(String fileName) {
    List<String[]> list = readCsvFile("repairs.csv");
    String repairDescription;
    LocalDate submissionDate;
    LocalDate startDate;
    double cost;
    boolean acceptance;
    RepairStatus repairStatus;
    List<Repair> repairs = new ArrayList<>();
    for (String[] temp : list) {
      repairDescription = temp[0];
      submissionDate = LocalDate.parse(temp[1]);
      startDate = LocalDate.parse(temp[2]);
      cost = Double.parseDouble(temp[3]);
      acceptance = Boolean.parseBoolean(temp[4]);
      repairStatus = RepairStatus.valueOf(temp[5]);
      repairs.add(new Repair(repairDescription, submissionDate, startDate, cost, acceptance, repairStatus));
      logger.info("The repair with has been loaded");
    }
    return repairs;
  }
}
