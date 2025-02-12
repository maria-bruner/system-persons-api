package com.system.person_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Entity
@Table(name = "persons")
public class PersonModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_person")
    private UUID id;
    @NotNull
    private String name;
    private String phone;
    @NotNull
    private String cpf;
    private String address;
    private String number;
    private String complementary;
    private String cep;
    private String neighborhood;
    private String city;
    private String state;

    public PersonModel() {}

    public PersonModel(UUID id, String name, String phone, String cpf, String address, String number, String complementary, String cep, String neighborhood, String city, String state) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.cpf = cpf;
        this.address = address;
        this.number = number;
        this.complementary = complementary;
        this.cep = cep;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
    }

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
}