package com.team04.technikon.dto;

import com.team04.technikon.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class Report {

  EntityManager entityManager = JpaUtil.getEntityManager();
  private final Properties sqlCommands = new Properties();

  {
    final ClassLoader loader = getClass().getClassLoader();
    try ( InputStream config = loader.getResourceAsStream("sql.properties")) {
      sqlCommands.load(config);
    } catch (IOException e) {
      throw new IOError(e);
    }
  }

  /**
   * This method prints the owner's properties .
   *
   * @param ownerId
   * @return
   */
  public void reportProperties(int ownerId) {
    System.out.println("This is a full report of your properties");
    TypedQuery<Tuple> query = entityManager.createQuery(sqlCommands.getProperty("select.report.properties"), Tuple.class)
            .setParameter("ownerid", ownerId);
    List<Tuple> resultList = query.getResultList();
    resultList.forEach(tuple -> System.out.println("Property id: " + tuple.get(0) + "| Address: " + tuple.get(1) + "| E9: " + tuple.get(2) + "| Property type: " + tuple.get(3) + "| Year of construction: " + tuple.get(4)));
  }

  /**
   * This method prints a list of the necessary information for the property
   * owner report .
   *
   * @param ownerId
   *
   */
  public void reportRepairStatuses(int ownerId) {
    System.out.println("These are the repair statuses of the repairs on your properties");
    TypedQuery<Tuple> query = entityManager.createQuery(sqlCommands.getProperty("select.report.property.repairStatus"), Tuple.class)
            .setParameter("ownerid", ownerId);
    List< Tuple> resultList = query.getResultList();
    resultList.forEach(tuple -> System.out.println("Property id: " + tuple.get(0) + "| Repair id: " + tuple.get(1) + "| Repair status: " + tuple.get(2)));
  }

  /**
   * This method prints a list of all the repairs on all statuses as needed for
   * the admins report.
   */
  public void reportRepairsInAllStatuses() {
    System.out.println("These are all the repairs in all statuses");
    TypedQuery<Tuple> query = entityManager.createQuery(sqlCommands.getProperty("select.report.repairs"), Tuple.class);
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
}
