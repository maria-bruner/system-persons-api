import axios from 'axios';

const BASE_URL = '/person-reports'

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
});

const getReports = async (page = 0, size = 10) => {
  try {
    const response = await api.get(BASE_URL, { params: { page, size } });
    return response.data.content;
  } catch (error) {
    console.error('Erro ao buscar relatórios:', error);
    return null;
  }
};

const createReport = async () => {
  try {
    const response = await api.post(BASE_URL);
    return response.data;
  } catch (error) {
    console.error('Erro ao criar relatório:', error);
    return null;
  }
};


const downloadReport = async (id) => {
  try {
    const response = await api.get(`${BASE_URL}/download/${id}`, {
      responseType: 'blob',
    });

    const url = window.URL.createObjectURL(new Blob([response.data]));
    const link = document.createElement('a');
    link.href = url;
    link.setAttribute('download', `relatorio-${id}.csv`);
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
  } catch (error) {
    console.error('Erro ao baixar relatório:', error);
  }
};

export { getReports, createReport, downloadReport };