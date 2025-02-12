import React, { useEffect, useState } from 'react';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Typography, Box, Button, IconButton } from '@mui/material';
import { CloudDownload } from '@mui/icons-material';
import { getReports, createReport, downloadReport } from '../services/ReportService';
import { formatDate } from '../utils/DateUtil'

const TableReport
 = () => {
  const [reports, setReports] = useState([]);

  const handlerReports = async () => {
    const response = await getReports();
    if (response) {
      setReports(response);
    }
  };

  useEffect(() => {
    handlerReports();
  }, []);

  const handlerGenerateReport = async () => {
    const newReport = await createReport();
    if (newReport) {
      handlerReports();
    }
  };

  const handlerDownloadReport = async (id) => {
    await downloadReport(id);
  };

  return (
    <Box sx={{ width: '100%', maxWidth: '1200px', margin: '0 auto', mt: 2 }}>
      <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 2 }}>
        <Typography variant="h5" sx={{ fontWeight: 'bold', color: '#422bff' }}>
          Relatórios
        </Typography>
        <Button variant="contained" color="primary" onClick={handlerGenerateReport}>
          Gerar Novo Relatório
        </Button>
      </Box>

      <TableContainer component={Paper} sx={{ width: '100%', minHeight: '400px' }}>
        <Table sx={{ minWidth: 650 }} aria-label="Tabela de Relatórios">
          <TableHead>
            <TableRow>
              <TableCell sx={{ fontWeight: 'bold' }}>Nome</TableCell>
              <TableCell sx={{ fontWeight: 'bold' }}>Data de Criação</TableCell>
              <TableCell sx={{ fontWeight: 'bold' }}>Status</TableCell>
              <TableCell sx={{ fontWeight: 'bold', textAlign: 'center' }}>Ações</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {reports?.map((report) => (
              <TableRow key={report.id}>
                <TableCell>{report.name}</TableCell>
                <TableCell>{formatDate(report.createdDate)}</TableCell>
                <TableCell>{report.status}</TableCell>
                <TableCell sx={{ textAlign: 'center' }}>
                  <IconButton color="primary" onClick={() => handlerDownloadReport(report.id)}>
                    <CloudDownload />
                  </IconButton>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Box>
  );
};

export default TableReport;