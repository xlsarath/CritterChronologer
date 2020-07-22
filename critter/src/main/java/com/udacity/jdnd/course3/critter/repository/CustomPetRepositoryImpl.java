package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Pet;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class CustomPetRepositoryImpl implements CustomPetRepository {


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Pet> getCustomersPets(Long customerId) {

        String query = "select p " +
                " from CustomerPetRelationship cpr " +
                " join Pet p on cpr.pet_id = p.id " +
                " where cpr.customer_id = " +
                customerId.toString();
        TypedQuery<Pet> typedQuery = entityManager.createQuery(query, Pet.class);

        return typedQuery.getResultList();

    }
}