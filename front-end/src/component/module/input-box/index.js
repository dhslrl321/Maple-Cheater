import React from 'react'

import { makeStyles } from '@material-ui/core'
import TextField from "@mui/material/TextField";
import Box from '@mui/material/Box';

const index = ({ styleType, name, label, handleOnChange, value, disabled }) => {

  const outlinedProps = {
    id: "outlined-basic",
    variant: "outlined",
    fullWidth: true
  }

  const multilineProps = {
    id: "outlined-multiline-static",
    multiline: true,
    fullWidth: true,
    rows: 5
  };

  if (styleType === "Simple") {
    return <TextField {...outlinedProps}
      disabled={disabled}
      name={name}
      value={value}
      onChange={handleOnChange}
      label={label} />
  } else if (styleType === "SimpleWithPrimaryColor") {
    return <TextField {...outlinedProps}
      label={label} />
  } else if (styleType === "Password") {
    return <TextField {...outlinedProps}
      name={name}
      label={label}
      onChange={handleOnChange}
      value={value}
      type="password" // 만약 비밀번호가 다르다면 error, helperText="비밀번호가 일치하지 않습니다." props 추가해서 wrong 띄우기
      autoComplete="current-password" />
  } else if (styleType === "Multiline") {
    return <TextField {...multilineProps}
      name={name}
      onChange={handleOnChange}
      label={label}
      value={value}
    />
  }

  return <></>
}

export default index
