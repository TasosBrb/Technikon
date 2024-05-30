package com.team04.technikon.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@MappedSuperclass
public class PersistentClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
}
