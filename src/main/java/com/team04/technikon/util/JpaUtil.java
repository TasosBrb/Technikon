package com.team04.technikon.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JpaUtil {

  private static final Logger logger = LogManager.getLogger(JpaUtil.class);

  private static final String PERSISTENCE_UNIT_NAME = "Persistence";
  private static EntityManagerFactory factory;

  public static EntityManagerFactory getEntityManagerFactory() {
    if (factory == null) {
      factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }
    return factory;
  }

  public static EntityManager getEntityManager() {
    return getEntityManagerFactory().createEntityManager();
  }

  /**
   * This method adds a persistent object in the database
   *
   * @param obj
   */
  public static void PersistObject(Object obj) {
    getEntityManager().getTransaction().begin();
    getEntityManager().persist(obj);
    getEntityManager().getTransaction().commit();

    logger.info("The object has been added");
  }

  /**
   * This method removes an object from the database
   *
   * @param obj
   */
  public static void RemoveObject(Object obj) {
    getEntityManager().getTransaction().begin();
    getEntityManager().remove(obj);
    getEntityManager().getTransaction().commit();

    logger.info("The object has been removed");
  }

  public static void shutdown() {
    if (factory != null) {
      factory.close();
      factory = null;
    }
  }
}
