package com.system.person_api.dto;

import com.system.person_api.model.PersonModel;

import java.util.UUID;

public class PersonDTO {

    private UUID id;
    private String name;
    private String phone;
    private String cpf;
    private String address;
    private String number;
    private String complementary;
    private String cep;
    private String neighborhood;
    private String city;
    private String state;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getComplementary() {
        return complementary;
    }

    public void setComplementary(String complementary) {
        this.complementary = complementary;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public static PersonDTO fromPersonModel(PersonModel personModel) {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(personModel.getId());
        personDTO.setPhone(personModel.getPhone());
        personDTO.setName(personModel.getName());
        personDTO.setCpf(personModel.getCpf());
        personDTO.setAddress(personModel.getAddress());
        personDTO.setNumber(personModel.getNumber());
        personDTO.setComplementary(personModel.getComplementary());
        personDTO.setCep(personModel.getCep());
        personDTO.setNeighborhood(personModel.getNeighborhood());
        personDTO.setCity(personModel.getCity());
        personDTO.setState(personModel.getState());
        return personDTO;
    }

    public PersonModel toPersonModel() {
        return new PersonModel(
                this.id,
                this.name,
                this.phone,
                this.cpf,
                this.address,
                this.number,
                this.complementary,
                this.cep,
                this.neighborhood,
                this.city,
                this.state
        );
    }
}