package com.system.person_api.repository;

import com.system.person_api.model.PersonModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<PersonModel, UUID> {

    Page<PersonModel> findByCpf(String cpf, Pageable pageable);

    Page<PersonModel> findByNameContainingIgnoreCase(String name, Pageable pageable);

}