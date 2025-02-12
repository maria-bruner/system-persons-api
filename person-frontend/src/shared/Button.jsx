import React from 'react';
import Button from '@mui/material/Button';

const CustomButton = ({ children, className, ...props }) => {
  return (
    <Button
      variant="contained"
      color="primary"
      sx={{
        padding: '10px 16px',
        borderRadius: '8px',
        textTransform: 'none',
        fontSize: '16px',
        fontWeight: 'bold',
        transition: 'background 0.3s',
      }}
      {...props}
    >
      {children}
    </Button>
  );
};

export default CustomButton;
