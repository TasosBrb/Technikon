package com.team04.technikon.services.impl;

import com.team04.technikon.model.Repair;
import com.team04.technikon.repository.PropertyOwnerRepository;
import com.team04.technikon.repository.PropertyRepository;
import com.team04.technikon.repository.RepairRepository;
import com.team04.technikon.services.AdminService;
import com.team04.technikon.util.JpaUtil;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdminServiceImpl implements AdminService {

  private static final Logger logger = LogManager.getLogger(AdminServiceImpl.class);
  private final PropertyOwnerRepository propertyOwnerRepository;
  private final PropertyRepository propertyRepository;
  private final RepairRepository repairRepository;
  private final Properties sqlCommands = new Properties();

  {
    final ClassLoader loader = getClass().getClassLoader();
    try ( InputStream config = loader.getResourceAsStream("sql.properties")) {
      sqlCommands.load(config);
    } catch (IOException e) {
      throw new IOError(e);
    }
  }

  public AdminServiceImpl(PropertyOwnerRepository propertyOwnerRepository, PropertyRepository propertyRepository, RepairRepository repairRepository) {
    this.propertyOwnerRepository = propertyOwnerRepository;
    this.propertyRepository = propertyRepository;
    this.repairRepository = repairRepository;
  }

  @Override
  public void displayPendingRepairs() {
    TypedQuery<Tuple> query = JpaUtil.getEntityManager().createQuery(sqlCommands.getProperty("select.pendingRepairs"), Tuple.class);
    List<Tuple> resultList = query.getResultList();
    resultList.forEach(tuple -> System.out.println("Property id: " + tuple.get(1)
            + "| Repair id: " + tuple.get(0)
            + "| Type of repair: " + tuple.get(9)
            + "| Repair description: " + tuple.get(7)
            + "| Submission date: " + tuple.get(11)
            + "| Work to be done: " + tuple.get(12)
            + "| Proposed start date: " + tuple.get(10)
            + "| Proposed end date: " + tuple.get(6)
            + "| Proposed cost: " + tuple.get(5)
            + "| Repair accepted: " + tuple.get(2)
            + "| Repair status: " + tuple.get(8)
            + "| Actual start date: " + tuple.get(4)
            + "| Actual end date: " + tuple.get(3)));
  }

  @Override
  public void proposeCosts(Repair repair, double cost) {
    try{  
    repairRepository.updateCost(repair.getId(), cost);
    logger.info("The cost of the repair with id {} has been proposed", repair.getId());
    }catch(Exception e){
        logger.warn("Can not propose the costs");
    }
  }

 @Override
  public void proposeDates(Repair repair, LocalDate startDate, LocalDate endDate) {
      
    try{  
    repairRepository.updateStartDate(repair.getId(), startDate);
    repairRepository.updateEndDate(repair.getId(), endDate);
    logger.info("The dates of the repair with id {} have been proposed", repair.getId());
    }catch(Exception e){
        logger.warn("Can not propose the dates");
    }
  }

  @Override
  public void displayActualDatesOfPendingRepairs() {
    TypedQuery<Tuple> query = JpaUtil.getEntityManager().createQuery(sqlCommands.getProperty("select.start.end.dates"), Tuple.class);
    List<Tuple> resultList = query.getResultList();
    resultList.forEach(tuple -> System.out.println(
            "| Repair id: " + tuple.get(0)
            + "| Actual start date: " + tuple.get(1)
            + "| Actual end date: " + tuple.get(2)));
  }
}
