import './App.css';
import Layout from './shared/Layout';
import TablePerson from './components/TablePerson';
import FormPerson from './components/FormPerson'; 
import TableReport from './components/TablePerson';
import Home from './components/Home' 
import { createTheme, ThemeProvider } from '@mui/material/styles';
import '@fontsource/poppins';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { useState } from 'react';

const theme = createTheme({
  typography: {
    fontFamily: 'Poppins, sans-serif',
  },
});

function App() {
  const [persons, setPersons] = useState([]);

  const handlerEditPerson = (id, dataUpdated) => {
    setPersons(persons.map(p => p.id === id ? { ...p, ...dataUpdated } : p));
  };

  return (
    <ThemeProvider theme={theme}>
      <Router>
        <Routes>
          <Route path="/adicionar-pessoa" element={<Layout><FormPerson onSubmit={handlerEditPerson} /></Layout>} />
          <Route 
            path="/editar-pessoa/:id" 
            element={<Layout><FormPerson onSubmit={handlerEditPerson} pessoas={persons} /></Layout>} 
          />
          <Route path="/cadastro-pessoas" element={<Layout><TablePerson pessoas={persons} setPessoas={setPersons} /></Layout>} />
          <Route path="/relatorios" element={<Layout><TableReport /></Layout>} />
          <Route path="/" element={
            <Layout>
              <Home />
            </Layout>
          } />
        </Routes>
      </Router>
    </ThemeProvider>
  );
}

export default App;