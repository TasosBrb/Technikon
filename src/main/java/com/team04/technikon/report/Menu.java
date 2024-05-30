package com.team04.technikon.report;

import com.team04.technikon.repository.PropertyOwnerRepository;
import com.team04.technikon.repository.PropertyRepository;
import com.team04.technikon.repository.RepairRepository;
import com.team04.technikon.repository.impl.PropertyOwnerRepositoryImpl;
import com.team04.technikon.repository.impl.PropertyRepositoryImpl;
import com.team04.technikon.repository.impl.RepairRepositoryImpl;
import com.team04.technikon.services.OwnerService;
import com.team04.technikon.services.impl.OwnerServiceImpl;
import com.team04.technikon.util.JpaUtil;
import java.util.Scanner;

public class Menu {

  /**
   * This method serves as a Ui for the application
   */
  public void menu() {

    PropertyRepository propertyRepository = new PropertyRepositoryImpl(JpaUtil.getEntityManager());
    PropertyOwnerRepository propertyOwnerRepository = new PropertyOwnerRepositoryImpl(JpaUtil.getEntityManager());
    RepairRepository repairRepository = new RepairRepositoryImpl(JpaUtil.getEntityManager());
    OwnerService ownerService = new OwnerServiceImpl(propertyOwnerRepository, propertyRepository, repairRepository);

    UseCases.useCaseOne();
    int choice;
    do {
      Scanner scanner = new Scanner(System.in);
      System.out.println("Choose an option from {1-9}");
      System.out.println("1: Owner registration with two properties");
      System.out.println("2: Repair registration");
      System.out.println("3: Repair administration");
      System.out.println("4: Administrator reports");
      System.out.println("5: Owner reports");
      System.out.println("6: Delete a property owner");
      System.out.println("7: Delete a property");
      System.out.println("8: Delete a repair");
      System.out.println("9: Exit the application");

      choice = scanner.nextInt();

      switch (choice) {
        case 1:
          ownerService.registerNewOwner();
          break;
        case 2:
          UseCases.useCaseThree();
          break;
        case 3:
          UseCases.useCaseFour();
          break;
        case 4:
          UseCases.useCaseFiveAdministrator();
          break;
        case 5:
          UseCases.useCaseFiveOwner();
          break;
        case 6:
          System.out.println("Enter the id of the property owner you want to delete");
          int ownerId = scanner.nextInt();
          propertyOwnerRepository.delete(ownerId);
          break;
        case 7:
          System.out.println("Enter the id of the property you want to delete");
          int propertyId = scanner.nextInt();
          propertyRepository.delete(propertyId);
          break;
        case 8:
          System.out.println("Enter the id of the repair you want to delete");
          int repairId = scanner.nextInt();
          repairRepository.delete(repairId);
          break;
        case 9:
          System.exit(0);
          break;
      }
    } while (choice != 9);

  }
}
