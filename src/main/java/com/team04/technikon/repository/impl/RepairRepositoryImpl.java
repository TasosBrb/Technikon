package com.team04.technikon.repository.impl;

import com.team04.technikon.enums.RepairStatus;
import com.team04.technikon.enums.RepairType;
import com.team04.technikon.model.Property;
import com.team04.technikon.model.Repair;
import com.team04.technikon.repository.RepairRepository;
import com.team04.technikon.util.JpaUtil;
import jakarta.persistence.EntityManager;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RepairRepositoryImpl extends RepositoryImpl<Repair> implements RepairRepository {

  private final Properties sqlCommands = new Properties();
  private static final Logger logger = LogManager.getLogger(RepairRepositoryImpl.class);

  {
    final ClassLoader loader = getClass().getClassLoader();
    try ( InputStream config = loader.getResourceAsStream("sql.properties")) {
      sqlCommands.load(config);
    } catch (IOException e) {
      throw new IOError(e);
    }
  }

  public RepairRepositoryImpl(EntityManager entityManager) {
    super(entityManager);
  }

  @Override
  public Repair search(int id) {
    return JpaUtil.getEntityManager().find(Repair.class, id);
  }

  @Override
  public List<Repair> search(LocalDate submissionDate) {
    return entityManager.createQuery("select x from repair x where x.submissionDate =: submissionDate ", Repair.class)
            .setParameter("submissionDate", submissionDate).getResultList();
  }

  @Override
  public void updatePropertyId(int repairId, int propertyId) {
    Repair repair = entityManager.find(Repair.class, repairId);
    Property property = entityManager.find(Property.class, propertyId);
    try {
      repair.setProperty(property);
      entityManager.getTransaction().begin();
      entityManager.merge(repair);
      entityManager.getTransaction().commit();
      logger.info("The Repair's property id has been updated");
    } catch (Exception ex) {
      logger.warn(" Can't be updated", ex);
    }
  }

  @Override
  public void updateRepairType(int id, RepairType repairType) {
    Repair repair = entityManager.find(Repair.class, id);
    repair.setRepairType(repairType);
    try {
      entityManager.getTransaction().begin();
      entityManager.persist(repair);
      entityManager.getTransaction().commit();
      logger.info("The Repair Type with id {} has been updated", repair.getId());
    } catch (Exception ex) {
      logger.warn(" Can't be updated", ex);
    }
  }

  @Override
  public void updateRepairDescription(int id, String repairDescription) {
    Repair repair = entityManager.find(Repair.class, id);
    repair.setRepairDescription(repairDescription);
    try {
      entityManager.getTransaction().begin();
      entityManager.persist(repair);
      entityManager.getTransaction().commit();
      logger.info("The Repair's description with id {} has been updated", repair.getId());
    } catch (Exception ex) {
      logger.warn(" Can't be updated", ex);
    }
  }

  @Override
  public void updateSubmissionDate(int id, LocalDate submissionDate) {
    Repair repair = entityManager.find(Repair.class, id);
    repair.setSubmissionDate(submissionDate);
    try {
      entityManager.getTransaction().begin();
      entityManager.persist(repair);
      entityManager.getTransaction().commit();
      logger.info("The Repair's submissin date with id {} has been updated", repair.getId());
    } catch (Exception ex) {
      logger.warn(" Can't be updated", ex);
    }
  }

  @Override
  public void updateWorkDescription(int id, String workDescription) {
    Repair repair = entityManager.find(Repair.class, id);
    repair.setWorkDescription(workDescription);
    try {
      entityManager.getTransaction().begin();
      entityManager.persist(repair);
      entityManager.getTransaction().commit();
      logger.info("The Repair's work description with id {} has been updated", repair.getId());
    } catch (Exception ex) {
      logger.warn(" Can't be updated", ex);
    }
  }

  @Override
  public void updateStartDate(int id, LocalDate startDate) {
    Repair repair = entityManager.find(Repair.class, id);
    repair.setStartDate(startDate);
  }

  @Override
  public void updateEndDate(int id, LocalDate endDate) {
    Repair repair = entityManager.find(Repair.class, id);
    repair.setEndDate(endDate);
    try {
      entityManager.getTransaction().begin();
      entityManager.persist(repair);
      entityManager.getTransaction().commit();
      logger.info("The Repair's end date with id {} has been updated", repair.getId());
    } catch (Exception ex) {
      logger.warn(" Can't be updated", ex);
    }
  }

  @Override
  public void updateCost(int id, double cost) {
    Repair repair = entityManager.find(Repair.class, id);
    repair.setCost(cost);
    try {
      entityManager.getTransaction().begin();
      entityManager.persist(repair);
      entityManager.getTransaction().commit();
      logger.info("The Repair's cost with id {} has been updated", repair.getId());
    } catch (Exception ex) {
      logger.warn(" Can't be updated", ex);
    }
  }

  @Override
  public void updateAcceptance(int id, boolean acceptance) {
    Repair repair = entityManager.find(Repair.class, id);
    repair.setAcceptance(acceptance);
    try {
      entityManager.getTransaction().begin();
      entityManager.persist(repair);
      entityManager.getTransaction().commit();
      logger.info("The Repair's acceptance with id {} has been updated", repair.getId());
    } catch (Exception ex) {
      logger.warn(" Can't be updated", ex);
    }
  }

  @Override
  public void updateRepairStatus(int id, RepairStatus repairStatus) {
    Repair repair = entityManager.find(Repair.class, id);
    repair.setRepairStatus(repairStatus);
    try {
      entityManager.getTransaction().begin();
      entityManager.persist(repair);
      entityManager.getTransaction().commit();
      logger.info("The Repair's status with id {} has been updated", repair.getId());
    } catch (Exception ex) {
      logger.warn(" Can't be updated", ex);
    }
  }

  @Override
  public void updateActualStartDate(int id, LocalDate actualStartDate) {
    Repair repair = entityManager.find(Repair.class, id);
    repair.setActualStartDate(actualStartDate);
    try {
      entityManager.getTransaction().begin();
      entityManager.persist(repair);
      entityManager.getTransaction().commit();
      logger.info("The Repair's actual start date with id {} has been updated", repair.getId());
    } catch (Exception ex) {
      logger.warn(" Can't be updated", ex);
    }
  }

  @Override
  public void updateActualEndDate(int id, LocalDate actualEndDate) {
    Repair repair = entityManager.find(Repair.class, id);
    repair.setActualEndDate(actualEndDate);
    try {
      entityManager.getTransaction().begin();
      entityManager.persist(repair);
      entityManager.getTransaction().commit();
      logger.info("The Repair's actual end date with id {} has been updated", repair.getId());
    } catch (Exception ex) {
      logger.warn(" Can't be updated", ex);
    }
  }

  @Override
  public boolean delete(int id) {
    Repair repair = entityManager.find(Repair.class, id);
    if (repair == null) {
      return false;
    }
    try {
      entityManager.getTransaction().begin();
      entityManager.remove(repair);
      entityManager.getTransaction().commit();
    } catch (Exception ex) {
      logger.warn("Can't be deleted", ex);
    }
    return true;
  }

  @Override
  public List<Repair> readAll() {
    List<Repair> results = JpaUtil.getEntityManager().createQuery(sqlCommands.getProperty("select.repairs"))
            .getResultList();
    return results;
  }

}
