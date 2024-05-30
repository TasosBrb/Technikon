package com.team04.technikon.repository.impl;

import com.team04.technikon.model.PersistentClass;
import com.team04.technikon.repository.Repository;
import jakarta.persistence.EntityManager;

public class RepositoryImpl<T extends PersistentClass> implements Repository<T> {

  protected EntityManager entityManager;

  public RepositoryImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public int create(T t) {
    entityManager.getTransaction().begin();
    entityManager.persist(t);
    entityManager.getTransaction().commit();
    return t.getId();
  }
}
