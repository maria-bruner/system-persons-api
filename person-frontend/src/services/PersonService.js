import axios from 'axios';

const BASE_URL = '/persons';

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
});

const createPerson = async (person) => {
  try {
    const response = await api.post(BASE_URL, person);
    return response.data;
  } catch (error) {
    console.error('Erro ao criar pessoa:', error);
    return null;
  }
};

const getPersonById = async (id) => {
  try {
    const response = await api.get(`${BASE_URL}/${id}`);
    return response.data;
  } catch (error) {
    console.error('Erro ao buscar pessoa:', error);
    return null;
  }
};

const updatePerson = async (id, person) => {
  try {
    const response = await api.put(`${BASE_URL}/${id}`, person);
    return response.data;
  } catch (error) {
    console.error('Erro ao atualizar pessoa:', error);
    return null;
  }
};

const deletePerson = async (id) => {
  try {
    await api.delete(`${BASE_URL}/${id}`);
    return true;
  } catch (error) {
    console.error('Erro ao excluir pessoa:', error);
    return false;
  }
};

const getAll = async () => {
  try {
    const response = await api.get(BASE_URL);
    return response.data.content;
  } catch (error) {
    console.error('Erro ao buscar pessoas:', error);
    return [];
  }
};

export { createPerson, updatePerson, getPersonById, deletePerson, getAll };