import { useState } from "react";
import Box from '@mui/material/Box';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import MuiSelect from '@mui/material/Select';

const Select = ({ title, name, label, menuItems, handleOnChange, value }) => {

  return (
    <Box sx={{ minWidth: 120 }}>
      <FormControl fullWidth>
        <InputLabel>{title}</InputLabel>
        <MuiSelect
          value={value}
          label={label}
          name={name}
          onChange={handleOnChange}
        >
          {menuItems.map(menuItem => <MenuItem key={menuItem.id} value={menuItem.id}>{menuItem.value}</MenuItem>)}
        </MuiSelect>
      </FormControl>
    </Box>
  )
}

export default Select
