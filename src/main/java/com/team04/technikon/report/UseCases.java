package com.team04.technikon.report;

import com.team04.technikon.dto.Report;
import com.team04.technikon.enums.PropertyType;
import com.team04.technikon.model.Property;
import com.team04.technikon.model.PropertyOwner;
import com.team04.technikon.model.Repair;
import com.team04.technikon.repository.PropertyOwnerRepository;
import com.team04.technikon.repository.PropertyRepository;
import com.team04.technikon.repository.RepairRepository;
import com.team04.technikon.repository.impl.PropertyOwnerRepositoryImpl;
import com.team04.technikon.repository.impl.PropertyRepositoryImpl;
import com.team04.technikon.repository.impl.RepairRepositoryImpl;
import com.team04.technikon.services.AdminService;
import com.team04.technikon.services.IoServices;
import com.team04.technikon.services.OwnerService;
import com.team04.technikon.services.impl.AdminServiceImpl;
import com.team04.technikon.services.impl.IoServiceImpl;
import com.team04.technikon.services.impl.OwnerServiceImpl;
import com.team04.technikon.util.JpaUtil;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class UseCases {

  /**
   * By using this method data(OWNERS,PROPERTIES,REPAIRS) will be inserted into
   * database
   */
  public static void useCaseOne() {
    IoServices services = new IoServiceImpl();
    List<Repair> repairs = services.loadRepairData("repairs.csv");
    List<PropertyOwner> propertyOwners = services.loadOwnerData("owners.csv");
    List<Property> properties = services.loadPropertyData("property.csv");
    PropertyRepository propertyRepository = new PropertyRepositoryImpl(JpaUtil.getEntityManager());
    PropertyOwnerRepository propertyOwnerRepository = new PropertyOwnerRepositoryImpl(JpaUtil.getEntityManager());
    RepairRepository repairRepository = new RepairRepositoryImpl(JpaUtil.getEntityManager());
    for (Repair repair : repairs) {
      repairRepository.create(repair);
    }
    for (PropertyOwner propertyOwner : propertyOwners) {
      propertyOwnerRepository.create(propertyOwner);
    }
    for (Property property : properties) {
      propertyRepository.create(property);
    }

    // ------------------------- Relationships between the objects  ------------------------- //
    propertyRepository.updateOwnerId(1, 1);
    propertyRepository.updateOwnerId(2, 2);
    propertyRepository.updateOwnerId(3, 2);
    propertyRepository.updateOwnerId(4, 3);
    propertyRepository.updateOwnerId(5, 4);
    propertyRepository.updateOwnerId(6, 5);
    propertyRepository.updateOwnerId(7, 6);
    propertyRepository.updateOwnerId(8, 7);
    propertyRepository.updateOwnerId(9, 8);
    propertyRepository.updateOwnerId(10, 7);
    propertyRepository.updateOwnerId(11, 9);
    propertyRepository.updateOwnerId(12, 10);
    propertyRepository.updateOwnerId(13, 11);
    propertyRepository.updateOwnerId(14, 12);
    propertyRepository.updateOwnerId(15, 13);
    propertyRepository.updateOwnerId(16, 14);
    propertyRepository.updateOwnerId(17, 15);
    propertyRepository.updateOwnerId(18, 16);
    propertyRepository.updateOwnerId(19, 17);
    propertyRepository.updateOwnerId(20, 18);
    repairRepository.updatePropertyId(1, 2);
    repairRepository.updatePropertyId(2, 1);
    repairRepository.updatePropertyId(3, 2);
    repairRepository.updatePropertyId(4, 5);
    repairRepository.updatePropertyId(5, 7);
    repairRepository.updatePropertyId(6, 8);
    repairRepository.updatePropertyId(7, 8);
    repairRepository.updatePropertyId(8, 10);
    repairRepository.updatePropertyId(9, 12);
    repairRepository.updatePropertyId(10, 20);
    repairRepository.updatePropertyId(11, 19);
    repairRepository.updatePropertyId(12, 15);
    repairRepository.updatePropertyId(13, 13);
    repairRepository.updatePropertyId(14, 13);
    repairRepository.updatePropertyId(15, 4);
    repairRepository.updatePropertyId(16, 3);
    repairRepository.updatePropertyId(17, 6);
    repairRepository.updatePropertyId(18, 11);
    repairRepository.updatePropertyId(19, 16);
    repairRepository.updatePropertyId(20, 17);
    // Updates for PropertyOwners
    propertyOwnerRepository.updateAddress(1, "Moran");
    propertyOwnerRepository.updateEmail(1, "Lasy");
    propertyOwnerRepository.updatePassword(1, "lasy123");
    // Updates for Properties
    propertyRepository.updateAddress(5, "Ledamonos 65");
    propertyRepository.updateOwnerVat(3, 426781512);
    propertyRepository.updateYearOfConstruction(4, "1980");
    propertyRepository.updatePropertyType(9, PropertyType.MAISONETTE);
    // Updates for Repairs
    repairRepository.updateAcceptance(1, false);
    repairRepository.updateCost(3, 450);
    repairRepository.updateStartDate(10, LocalDate.parse("2020-05-18"));
    repairRepository.updateEndDate(7, LocalDate.parse("2021-01-11"));
    repairRepository.updateActualEndDate(20, LocalDate.parse("2010-02-06"));
    repairRepository.updateActualStartDate(17, LocalDate.parse("2018-04-02"));
    // Update work description for Repairs
    repairRepository.updateWorkDescription(1, "Under Construction");
    repairRepository.updateWorkDescription(2, "Repaired successfully");
    repairRepository.updateWorkDescription(3, "Under Construction");
    repairRepository.updateWorkDescription(4, "Recently started");
    repairRepository.updateWorkDescription(5, "Under control");
    repairRepository.updateWorkDescription(6, "Repaired successfully");
    repairRepository.updateWorkDescription(7, "Repaired successfully");
    repairRepository.updateWorkDescription(8, "Repair of building completed");
    repairRepository.updateWorkDescription(9, "Recently started");
    repairRepository.updateWorkDescription(10, "50% of the work done");
    repairRepository.updateWorkDescription(11, "Under construction");
    repairRepository.updateWorkDescription(12, "Under construction");
    repairRepository.updateWorkDescription(13, "Repaired successfully");
    repairRepository.updateWorkDescription(14, "80% of the work done");
    repairRepository.updateWorkDescription(15, "Under construction");
    repairRepository.updateWorkDescription(16, "Recently started");
    repairRepository.updateWorkDescription(17, "Under construction");
    repairRepository.updateWorkDescription(18, "Under construction");
    repairRepository.updateWorkDescription(19, "Work recently started");
    repairRepository.updateWorkDescription(20, "Fixed");
  }

  /**
   * Method for repair registration, a property-owner is selected, and the
   * corresponding properties are displayed. For a particular property a repair
   * is registered.
   */
  public static void useCaseThree() {
    PropertyRepository propertyRepository = new PropertyRepositoryImpl(JpaUtil.getEntityManager());
    PropertyOwnerRepository propertyOwnerRepository = new PropertyOwnerRepositoryImpl(JpaUtil.getEntityManager());
    RepairRepository repairRepository = new RepairRepositoryImpl(JpaUtil.getEntityManager());
    OwnerService ownerService = new OwnerServiceImpl(propertyOwnerRepository, propertyRepository, repairRepository);
    ownerService.displayAllOwners();
    Scanner scanner = new Scanner(System.in);
    System.out.println("Give the ID of the owner you want : ");
    int ownerId = scanner.nextInt();
    ownerService.displayOwnersProperties(ownerId);
    System.out.println("Give the ID of the property you want : ");
    int propertyId = scanner.nextInt();
    Property property = JpaUtil.getEntityManager().find(Property.class, propertyId);
    ownerService.registerRepair(property);
  }

  /**
   * Repair administration,the admin gets all pending repairs, proposes the
   * costs, start and end dates. The property-owners accept or decline the
   * repairs. The admin checks the start of works and the end when they are
   * done.
   */
  public static void useCaseFour() {
    PropertyRepository propertyRepository = new PropertyRepositoryImpl(JpaUtil.getEntityManager());
    PropertyOwnerRepository propertyOwnerRepository = new PropertyOwnerRepositoryImpl(JpaUtil.getEntityManager());
    RepairRepository repairRepository = new RepairRepositoryImpl(JpaUtil.getEntityManager());
    OwnerService ownerService = new OwnerServiceImpl(propertyOwnerRepository, propertyRepository, repairRepository);
    AdminService adminService = new AdminServiceImpl(propertyOwnerRepository, propertyRepository, repairRepository);
    adminService.displayPendingRepairs();
    Scanner scanner = new Scanner(System.in);
    System.out.println("Select the id of the repair you want to configure: ");
    int repairId = scanner.nextInt();
    Repair repair = JpaUtil.getEntityManager().find(Repair.class, repairId);
    System.out.println("Propose cost of the repair: ");
    double cost = scanner.nextDouble();
    adminService.proposeCosts(repair, cost);
    System.out.println("Propose the start date: ");
    LocalDate startDate = LocalDate.parse(scanner.next());
    System.out.println("Propose the end date: ");
    LocalDate endDate = LocalDate.parse(scanner.next());
    adminService.proposeDates(repair, startDate, endDate);
    System.out.println("Does the owner accept the repair (1/2)");
    System.out.println("1.YES");
    System.out.println("2.NO");
    int response = scanner.nextInt();
    if (response == 1) {
      ownerService.acceptRepair(repair);
    } else {
      ownerService.declineRepair(repair);
    }
    adminService.displayPendingRepairs();
    System.out.println("These are the actual start and end dates of the repair");
    adminService.displayActualDatesOfPendingRepairs();
  }

  public static void useCaseFiveAdministrator() {
    Report report = new Report();
    Scanner scanner1 = new Scanner(System.in);
    System.out.println("Enter owner id");
    int ownerId = scanner1.nextInt();
    report.reportProperties(ownerId);
    report.reportRepairStatuses(ownerId);
  }

  public static void useCaseFiveOwner() {
    Report report = new Report();
    report.reportRepairsInAllStatuses();
  }
}
