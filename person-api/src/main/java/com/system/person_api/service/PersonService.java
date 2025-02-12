package com.system.person_api.service;

import com.system.person_api.dto.PersonDTO;
import com.system.person_api.exception.PersonNotFoundException;
import com.system.person_api.model.PersonModel;
import com.system.person_api.repository.PersonRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /**
     * Retorna uma pessoa com base no ID.
     *
     * @param id O ID da pessoa.
     * @return A pessoa encontrada, ou um Optional vazio se não encontrada.
     */
    public Optional<PersonModel> getPersonById(UUID id) {
        return personRepository.findById(id);
    }

    /**
     * Retorna uma página de pessoas filtradas por CPF ou nome.
     * Se nenhum filtro for fornecido, retorna todas as pessoas.
     *
     * @param cpf  O CPF para filtro.
     * @param name O nome para filtro.
     * @param page O número da página.
     * @param size O número de itens por página.
     * @return Uma página de {@link PersonDTO} com as pessoas filtradas.
     */
    public Page<PersonDTO> getPersons(String cpf, String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<PersonModel> personModels;

        if (cpf != null && !cpf.isEmpty()) {
            personModels = personRepository.findByCpf(cpf, pageable);
        } else if (name != null && !name.isEmpty()) {
            personModels = personRepository.findByNameContainingIgnoreCase(name, pageable);
        } else {
            personModels = personRepository.findAll(pageable);
        }

        return personModels.map(PersonDTO::fromPersonModel);
    }

    /**
     * Deleta uma pessoa pelo ID.
     *
     * @param id O ID da pessoa a ser deletada.
     */
    public void deletePersonById(UUID id) {
        personRepository.deleteById(id);
    }

    /**
     * Cria uma nova pessoa a partir de um {@link PersonDTO}.
     *
     * @param personDTO Os dados da pessoa a serem criados.
     * @return A pessoa criada.
     */
    public PersonModel createPerson(PersonDTO personDTO) {
        return personRepository.save(personDTO.toPersonModel());
    }

    /**
     * Atualiza os dados de uma pessoa com base no CPF.
     * Lança uma exceção caso a pessoa não seja encontrada.
     *
     * @param personDTO Os dados da pessoa a serem atualizados.
     * @return A pessoa atualizada.
     * @throws PersonNotFoundException Se a pessoa não for encontrada pelo CPF.
     */
    public PersonModel updatePerson(PersonDTO personDTO) throws PersonNotFoundException {
        return personRepository.findByCpf(personDTO.getCpf(), PageRequest.of(0, 1))
                .stream()
                .findFirst()
                .map(existingPerson -> {
                    existingPerson.setName(personDTO.getName());
                    existingPerson.setPhone(personDTO.getPhone());
                    existingPerson.setCpf(personDTO.getCpf());
                    existingPerson.setCep(personDTO.getCep());
                    existingPerson.setAddress(personDTO.getAddress());
                    existingPerson.setNumber(personDTO.getNumber());
                    existingPerson.setCity(personDTO.getCity());
                    existingPerson.setState(personDTO.getState());
                    existingPerson.setNeighborhood(personDTO.getNeighborhood());
                    existingPerson.setComplementary(personDTO.getComplementary());
                    return personRepository.save(existingPerson);
                })
                .orElseThrow(() -> new PersonNotFoundException("Pessoa não encontrada com CPF: " + personDTO.getCpf()));
    }

    /**
     * Retorna todas as pessoas ordenadas por nome em ordem crescente.
     *
     * @return Uma lista de pessoas ordenadas por nome.
     */
    public List<PersonModel> getAllPersonsSortedByName() {
        return personRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }
}