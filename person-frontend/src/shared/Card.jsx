import React from 'react';
import { Card as MuiCard, CardContent } from '@mui/material';

const CustomCard = ({ children, className, ...props }) => {
  return (
    <MuiCard
      sx={{
        borderRadius: '12px',
        boxShadow: 3,
        backgroundColor: 'white',
        padding: '16px',
      }}
      {...props}
    >
      <CardContent>{children}</CardContent>
    </MuiCard>
  );
};

export default CustomCard;