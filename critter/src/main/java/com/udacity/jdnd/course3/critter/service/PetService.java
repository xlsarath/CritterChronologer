package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional()
public class PetService {

    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;

    public PetService(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    public Pet savePet(Pet pet, long customerId) {

        // Step 1. Get the customer of the pet with customer id
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (!optionalCustomer.isPresent()) {
            return null;
        }

        Customer customer = optionalCustomer.get();

        pet.setCustomer(customer);

        // Step2. Save the pet
        Pet insertedPet = petRepository.save(pet);

        // Step3. Add the new pet to the customer pets and save it
        customer.addPet(insertedPet);
        customerRepository.save(customer);

        return insertedPet;
    }

    public Optional<Pet> getPet(long petId) {
        return petRepository.findById(petId);
    }

    public List<Pet> getPets() {
        return petRepository.findAll();
    }

    public List<Pet> getPetsByOwner(long customerId) {
        // Step 1. Get the customer of the pet with customer id
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (!optionalCustomer.isPresent()) {
            return null;
        }
        return petRepository.getCustomersPets(customerId);
    }


}