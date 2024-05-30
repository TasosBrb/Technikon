package com.team04.technikon.services;

import com.team04.technikon.model.Repair;
import java.time.LocalDate;

public interface AdminService {

  /**
   * This method prints a list of all pending repairs
   *
   * @return
   */
  void displayPendingRepairs();

  /**
   * This method updates the cost value to the proposed by the admin
   *
   * @param repair
   */
  void proposeCosts(Repair repair, double cost);

  /**
   * This method sets the proposed by the admin Dates of the repair.
   *
   * @param repair
   * @param startDate
   * @param endDate
   */
  void proposeDates(Repair repair, LocalDate startDate, LocalDate endDate);

  /**
   * This method displays a list of the final start and end dates of the repairs
   *
   * @return
   */
  void displayActualDatesOfPendingRepairs();

}
