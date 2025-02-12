import axios from 'axios';

const BASE_URL = '/json';

const api = axios.create({
  baseURL: 'https://viacep.com.br/ws/',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
});

const getDataCep = async (cep) => {
  try {
    const response = await api.get(`${cep}/${BASE_URL}`)
    return response.data
  } catch(error) {
    return null
  }
}

export { getDataCep }