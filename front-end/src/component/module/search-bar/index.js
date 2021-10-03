import React from 'react'

import { makeStyles } from '@material-ui/core'
import TextField from '@mui/material/TextField';

import Button from "../button";

import * as S from "./styles";

const useStyles = makeStyles((theme) => ({
  box: {
    placeContent: "0.4rem",
    '&.MuiOutlinedInput-root': {
      '& fieldset': {            // - The <fieldset> inside the Input-root
        borderColor: '#5F56EF',   // - Set the Input border
      },
      '&.Mui-focused fieldset': { // - Set the Input border when parent is focused 
        borderColor: '#5F56EF',
      },
    },
  }
}));

const index = ({ handleOnChange, handleSearchOnClick, searchText, loading }) => {

  const classes = useStyles();

  return (
    <S.Container>
      <S.SearchBarWrapper>
        <TextField
          fullWidth
          InputProps={{ className: classes.box }}
          id="outlined-basic"
          label="거래를 진행중인 상대방의 캐릭터 닉네임을 입력해주세요"
          variant="outlined"
          className={classes.textField}
          onChange={handleOnChange}
          value={searchText}
        />
        <Button
          label="조회"
          width={100}
          height={55}
          handleOnClick={handleSearchOnClick}
          disabled={loading} />
      </S.SearchBarWrapper>
    </S.Container>
  )
}

export default index
