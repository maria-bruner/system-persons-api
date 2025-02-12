import React, { useEffect, useState } from 'react';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Typography, Box, Button, IconButton } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import { getAll, deletePerson } from '../services/PersonService';

const TablePerson = () => {
  const navigate = useNavigate();
  const [persons, setPersons] = useState([]);

  const handlerPersons = async () => {
    const dados = await getAll();
    console.log(dados)
    if (dados) {
      setPersons(dados);
    }
  };

  const handlerDeletePerson = async (id) => {
    const sucesso = await deletePerson(id);
    if (sucesso) {
      setPersons(persons.filter(pessoa => pessoa.id !== id));
    }
  };

  const handlerAddress = (person) => {
    return `${person.address}, ${person.number} - ${person.neighborhood}, ${person.city} - ${person.state}, ${person.cep}`
  }

  useEffect(() => {
    handlerPersons();
  }, []);

  return (
    <Box sx={{ width: '100%', maxWidth: '1200px', margin: '0 auto', mt: 2 }}>
      <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 2 }}>
        <Typography variant="h5" sx={{ fontWeight: 'bold', color: '#422bff' }}>
          Pessoas
        </Typography>
        <Button 
          variant="contained" 
          color="primary" 
          onClick={() => navigate('/adicionar-pessoa')}
        >
          Adicionar Pessoa
        </Button>
      </Box>

      <TableContainer component={Paper} sx={{ width: '100%', minHeight: '400px' }}>
        <Table sx={{ minWidth: 650 }} aria-label="Tabela de Pessoas">
          <TableHead>
            <TableRow>
              <TableCell sx={{ fontWeight: 'bold' }}>Nome</TableCell>
              <TableCell sx={{ fontWeight: 'bold' }}>CPF</TableCell>
              <TableCell sx={{ fontWeight: 'bold' }}>Telefone</TableCell>
              <TableCell sx={{ fontWeight: 'bold' }}>Endereço</TableCell>
              <TableCell sx={{ fontWeight: 'bold', textAlign: 'center' }}>Ações</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {persons.length > 0 ? (
              persons.map((person) => (
                <TableRow key={person.id}>
                  <TableCell>{person.name}</TableCell>
                  <TableCell>{person.cpf}</TableCell>
                  <TableCell>{person.phone}</TableCell>
                  <TableCell>{handlerAddress(person)}</TableCell>
                  <TableCell sx={{ textAlign: 'center' }}>
                    <IconButton color="primary" onClick={() => navigate(`/editar-pessoa/${person.id}`)}>
                      <EditIcon />
                    </IconButton>
                    <IconButton color="error" onClick={() => handlerDeletePerson(person.id)}>
                      <DeleteIcon />
                    </IconButton>
                  </TableCell>
                </TableRow>
              ))
            ) : (
              <TableRow>
                <TableCell colSpan={5} align="center">
                  Nenhuma pessoa cadastrada.
                </TableCell>
              </TableRow>
            )}
          </TableBody>
        </Table>
      </TableContainer>
    </Box>
  );
};

export default TablePerson;