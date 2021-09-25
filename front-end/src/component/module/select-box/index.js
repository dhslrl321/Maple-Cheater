import React from 'react'

import Select from "@mui/material/Select";
import MenuItem from '@mui/material/MenuItem';
import InputLabel from '@mui/material/InputLabel';
import FormControl from '@mui/material/FormControl';

const index = ({ label, value, setValue, items }) => {

  const handleOnChange = (e) => {
    setValue(e.target.value);
  }

  return (
    <FormControl fullWidth >
      <InputLabel id="demo-simple-box-label">{label}</InputLabel>
      <Select
        labelId="demo-simple-select-label"
        id="demo-simple-select"
        value={value}
        label={label}
        onChange={handleOnChange}
      >
        {items.map(item => (
          <MenuItem value={item.id}>
            {item.name}
          </MenuItem>))}
      </Select>
    </FormControl>
  )
}

export default index
