import React from 'react';
import { CardContent as MuiCardContent } from '@mui/material';

const CustomCardContent = ({ children, className, ...props }) => {
  return (
    <MuiCardContent
      sx={{
        padding: '16px',
      }}
      {...props}
    >
      {children}
    </MuiCardContent>
  );
};

export default CustomCardContent;
