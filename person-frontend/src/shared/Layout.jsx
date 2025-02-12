import React, { useState } from 'react';
import { AppBar, Toolbar, IconButton, Drawer, List, ListItem, ListItemButton, ListItemIcon, ListItemText, Box } from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import PersonIcon from '@mui/icons-material/Person';
import AssessmentIcon from '@mui/icons-material/Assessment';
import { Link, useNavigate } from 'react-router-dom';

const drawerWidth = 240;

const Layout = ({ children }) => {
  const [open, setOpen] = useState(false);
  const navigate = useNavigate(); 

  const toggleDrawer = () => {
    setOpen(!open);
  };

  const handlerLogoClick = () => {
    navigate('/'); 
  };

  return (
    <Box sx={{ display: 'flex', minHeight: '100vh', overflow: 'hidden' }}>
      <AppBar position="fixed" sx={{ backgroundColor: 'white' }}>
        <Toolbar>
          <IconButton edge="start" color="primary" aria-label="menu" onClick={toggleDrawer} sx={{ mr: 2 }}>
            <MenuIcon />
          </IconButton>

          <Box
            component="img"
            src="https://img.freepik.com/premium-vector/human-family-unity-logo-icon-illustration-together-luxury-people-logo-line_620768-459.jpg?w=740"
            alt="Logo"
            sx={{ height: '80px', width: '100px', mr: 2, cursor: 'pointer' }}
            onClick={handlerLogoClick} 
          />
        </Toolbar>
      </AppBar>

      <Drawer variant="temporary" open={open} onClose={toggleDrawer} sx={{ '& .MuiDrawer-paper': { width: drawerWidth } }}>
        <List>
          <ListItem disablePadding>
            <ListItemButton component={Link} to="/cadastro-pessoas">
              <ListItemIcon><PersonIcon /></ListItemIcon>
              <ListItemText primary="Gestão de Pessoas" />
            </ListItemButton>
          </ListItem>
          <ListItem disablePadding>
            <ListItemButton component={Link} to="/relatorios">
              <ListItemIcon><AssessmentIcon /></ListItemIcon>
              <ListItemText primary="Relatório de Gestão de Pessoas" />
            </ListItemButton>
          </ListItem>
        </List>
      </Drawer>

      <Box 
        component="main" 
        sx={{ 
          flexGrow: 1, 
          p: 3, 
          mt: 8, 
          display: 'flex', 
          justifyContent: 'center', 
          alignItems: 'center',
          overflow: 'hidden' 
        }}
      >
        {children}
      </Box>
    </Box>
  );
};

export default Layout;