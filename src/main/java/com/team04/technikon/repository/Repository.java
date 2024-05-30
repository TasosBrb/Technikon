package com.team04.technikon.repository;

import com.team04.technikon.model.PersistentClass;

public interface Repository<T extends PersistentClass> {
  int create(T t);
}
