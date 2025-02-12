import React, { useState, useEffect } from 'react';
import { TextField, Button, Grid, Box, Typography } from '@mui/material';
import { useNavigate, useParams } from 'react-router-dom';
import { getPersonById, createPerson, updatePerson } from '../services/PersonService';
import { getDataCep } from '../services/CepService'

const FormPerson = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const isEditing = Boolean(id);

  const [formValues, setFormValues] = useState({
    id: id ?? null,
    name: '',
    cpf: '',
    phone: '',
    cep: '',
    neighborhood: '',
    address: '',
    number: '',
    complementary: '',
    city: '',
    state: '',
  });

  useEffect(() => {
    if (isEditing) {
      const fetchPerson = async () => {
        const person = await getPersonById(id);
        if (person) {
          setFormValues({
            id: id ?? null,
            name: person.name || '',
            cpf: person.cpf || '',
            phone: person.phone || '',
            cep: person.cep || '',
            neighborhood: person.neighborhood || '',
            address: person.address || '',
            number: person.number || '',
            complementary: person.complementary || '',
            city: person.city || '',
            state: person.state || '',
          });
        }
      };
      fetchPerson();
    }
  }, [id, isEditing]);

  const handlerChange = (e) => {
    const { name, value } = e.target;
    setFormValues((prevValues) => ({
      ...prevValues,
      [name]: value,
    }));
  };

  const handlerSubmit = async (e) => {
    e.preventDefault();

    if (isEditing) {
      await updatePerson(id, formValues);
    } else {
      await createPerson(formValues);
    }

    navigate('/cadastro-pessoas');
  };

  const handlerCancel = () => {
    navigate('/cadastro-pessoas');
  };

  const handlerCep = async (e) => {
    const { name, value } = e.target;
    setFormValues((prevValues) => ({
      ...prevValues,
      [name]: value,
    }));

    const data = await getDataCep(value)
    if (!!data) {
      setFormValues((prevValues) => ({
        ...prevValues,
        address: data.logradouro,
        city: data.localidade,
        neighborhood: data.bairro,
        state: data.estado
      }));
    }
  }

  return (
    <Box sx={{ padding: 3 }}>
      <Typography variant="h6" gutterBottom>
        {isEditing ? 'Editar Pessoa' : 'Adicionar Pessoa'}
      </Typography>
      <form onSubmit={handlerSubmit}>
        <Grid container spacing={2}>
          <Grid item xs={12} sm={6}>
            <TextField label="Nome" variant="outlined" fullWidth name="name" value={formValues.name} onChange={handlerChange} />
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField label="CPF" variant="outlined" fullWidth name="cpf" value={formValues.cpf} onChange={handlerChange} />
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField label="Telefone" variant="outlined" fullWidth name="phone" value={formValues.phone} onChange={handlerChange} />
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField label="CEP" variant="outlined" fullWidth name="cep" value={formValues.cep} onChange={handlerCep} />
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField label="Bairro" variant="outlined" fullWidth name="neighborhood" value={formValues.neighborhood} onChange={handlerChange} disabled={true} />
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField label="Rua" variant="outlined" fullWidth name="address" value={formValues.address} onChange={handlerChange} disabled={true} />
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField label="Cidade" variant="outlined" fullWidth name="city" value={formValues.city} onChange={handlerChange} disabled={true} />
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField label="Estado" variant="outlined" fullWidth name="state" value={formValues.state} onChange={handlerChange} disabled={true} />
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField label="Número" variant="outlined" fullWidth name="number" value={formValues.number} onChange={handlerChange} />
          </Grid>
          <Grid item xs={12} sm={6}>
            <TextField label="Complemento" variant="outlined" fullWidth name="complementary" value={formValues.complementary} onChange={handlerChange} />
          </Grid>
        </Grid>
        <Box sx={{ marginTop: 2 }}>
          <Button variant="contained" color="primary" type="submit" sx={{ marginRight: 2 }}>
            {isEditing ? 'Salvar alterações' : 'Adicionar'}
          </Button>
          <Button variant="outlined" color="secondary" onClick={handlerCancel}>
            Cancelar
          </Button>
        </Box>
      </form>
    </Box>
  );
};

export default FormPerson;