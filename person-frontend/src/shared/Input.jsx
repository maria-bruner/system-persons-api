import React from 'react';
import { TextField } from '@mui/material';

const CustomInput = ({ className, ...props }) => {
  return (
    <TextField
      variant="outlined"
      fullWidth
      sx={{
        '& .MuiOutlinedInput-root': {
          borderRadius: '8px',
        },
        '& .MuiOutlinedInput-notchedOutline': {
          borderColor: 'gray',
        },
        '&:hover .MuiOutlinedInput-notchedOutline': {
          borderColor: 'blue',
        },
        '&.Mui-focused .MuiOutlinedInput-notchedOutline': {
          borderColor: 'blue',
        },
      }}
      {...props}
    />
  );
};

export default CustomInput;
