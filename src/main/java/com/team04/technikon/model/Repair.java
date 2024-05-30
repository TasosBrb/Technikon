package com.team04.technikon.model;

import com.team04.technikon.enums.RepairStatus;
import com.team04.technikon.enums.RepairType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "repair")
@Table
public class Repair extends PersistentClass {

  @ManyToOne
  @JoinColumn(name = "property_id")
  private Property property;
  @Enumerated(EnumType.STRING)
  private RepairType repairType;
  private String repairDescription;
  private LocalDate submissionDate;
  private String workDescription;
  private LocalDate startDate;
  private LocalDate endDate;
  private double cost;
  private boolean acceptance;
  @Enumerated(EnumType.STRING)
  private RepairStatus repairStatus;
  private LocalDate actualStartDate;
  private LocalDate actualEndDate;

  public Repair(String repairDescription, LocalDate submissionDate, LocalDate startDate, double cost, boolean acceptance, RepairStatus repairStatus) {
    this.repairDescription = repairDescription;
    this.submissionDate = submissionDate;
    this.startDate = startDate;
    this.cost = cost;
    this.acceptance = acceptance;
    this.repairStatus = repairStatus;
    this.actualStartDate = actualStartDate;
    this.actualEndDate = actualEndDate;
  }

}
