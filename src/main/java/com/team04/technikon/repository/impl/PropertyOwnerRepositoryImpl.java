package com.team04.technikon.repository.impl;

import com.team04.technikon.model.Property;
import com.team04.technikon.model.PropertyOwner;
import com.team04.technikon.repository.PropertyOwnerRepository;
import com.team04.technikon.util.JpaUtil;
import jakarta.persistence.EntityManager;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PropertyOwnerRepositoryImpl extends RepositoryImpl<PropertyOwner> implements PropertyOwnerRepository {

  private static final Logger logger = LogManager.getLogger(PropertyOwnerRepositoryImpl.class);
  private final Properties sqlCommands = new Properties();

  {
    final ClassLoader loader = getClass().getClassLoader();
    try ( InputStream config = loader.getResourceAsStream("sql.properties")) {
      sqlCommands.load(config);
    } catch (IOException e) {
      throw new IOError(e);
    }
  }

  public PropertyOwnerRepositoryImpl(EntityManager entityManager) {
    super(entityManager);
  }

  @Override
  public PropertyOwner search(int id) {
    return JpaUtil.getEntityManager().find(PropertyOwner.class, id);
  }

  @Override
  public PropertyOwner search(String email) {
    return JpaUtil.getEntityManager().find(PropertyOwner.class, email);
  }

  @Override
  public void updateAddress(int id, String address) {
    PropertyOwner propertyOwner = entityManager.find(PropertyOwner.class, id);
    try {
      propertyOwner.setAddress(address);
      entityManager.getTransaction().begin();
      entityManager.persist(propertyOwner);
      entityManager.getTransaction().commit();
      logger.info("The owner's address has been updated. Owne's VAT : {}.", propertyOwner.getVat());
    } catch (Exception e) {
      logger.warn("Can't be upadated", e);
    }
  }

  @Override
  public void updateEmail(int id, String email) {
    PropertyOwner propertyOwner = entityManager.find(PropertyOwner.class, id);
    propertyOwner.setEmail(email);
    entityManager.getTransaction().begin();
    entityManager.persist(propertyOwner);
    entityManager.getTransaction().commit();
    logger.info("The owner's email has been updated. Owne's VAT : {}.", propertyOwner.getVat());
  }

  @Override
  public void updatePassword(int id, String password) {
    PropertyOwner propertyOwner = entityManager.find(PropertyOwner.class, id);
    try {
      propertyOwner.setPassword(password);
      entityManager.getTransaction().begin();
      entityManager.persist(propertyOwner);
      entityManager.getTransaction().commit();
      logger.info("The owner's password has been updated. Owne's VAT : {}.", propertyOwner.getVat());
    } catch (Exception e) {
      logger.warn("Can't be upadated", e);
    }
  }

  @Override
  public boolean delete(int id) {
    List<Property> propertyList = entityManager.createQuery("select p from property p where p.owner.id=:ownerId", Property.class)
            .setParameter("ownerId", id).getResultList();
    try {
      for (Property property : propertyList) {
        entityManager.getTransaction().begin();
        entityManager.find(Property.class, property.getId());
        entityManager.remove(property);
        entityManager.getTransaction().commit();
      }
      PropertyOwner propertyOwner = entityManager.find(PropertyOwner.class, id);
      entityManager.getTransaction().begin();
      entityManager.remove(propertyOwner);
      entityManager.getTransaction().commit();
      logger.info("Deletion completed successfully");
    } catch (Exception e) {
      logger.warn("Can't be deleted", e);
    }
    return true;
  }

  @Override
  public List<PropertyOwner> read(String ownerName) {
    return entityManager.createQuery(sqlCommands.getProperty("select.owner.byName"), PropertyOwner.class).setParameter("name", ownerName).getResultList();
  }

  @Override
  public List<PropertyOwner> readAll() {
    List<PropertyOwner> results = JpaUtil.getEntityManager().createQuery(sqlCommands.getProperty("select.owners"))
            .getResultList();
    return results;
  }
}
