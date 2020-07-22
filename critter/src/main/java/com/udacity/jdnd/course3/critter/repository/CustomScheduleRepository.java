package com.udacity.jdnd.course3.critter.repository;


import com.udacity.jdnd.course3.critter.entity.Schedule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomScheduleRepository {

    @Query(value = "select s.* from schedule s join schedule_pets sp on s.id = sp.schedule_id where sp.pets_id = :petId",
            nativeQuery = true)
    List<Schedule> getScheduleForPet(long petId);

    @Query(value = "select s.* from schedule s join  schedule_employees se on s.id = se.schedule_id where se.employees_id = :employeeId",
            nativeQuery = true)
    List<Schedule> getScheduleForEmployee(long employeeId);

    @Query(value = "select s.* from schedule s " +
            " join schedule_pets sp on s.id = sp.schedule_id " +
            " join customerid_petid_map c on sp.pets_id = c.pet_id " +
            " where c.customer_id = :customerId",
            nativeQuery = true)
    List<Schedule> getScheduleForCustomer(long customerId);


}