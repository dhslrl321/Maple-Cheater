import React from 'react'
import * as S from "./styles";

import Box from '@mui/material/Box';
import CircularProgress from '@mui/material/CircularProgress';

const Loading = () => {
  return (
    <S.Container>
      <Box sx={{ display: 'flex' }}>
        <CircularProgress />
      </Box>
    </S.Container>
  )
}

export default Loading;
