import React from 'react'

import CircularProgress from "@mui/material/CircularProgress";
import * as S from "./styles";

const index = ({
  handleOnClick,
  label,
  width,
  height,
  bold,
  withoutMargin,
  disabled,
  loading }) => {

  const content = loading
    ? <div><CircularProgress color="white" size={20} /></div>
    : label;

  return (
    <S.Button
      onClick={handleOnClick}
      width={width}
      height={height}
      bold={bold}
      disabled={disabled}
      withoutMargin={withoutMargin}
    >
      {content}
    </S.Button>
  )
}

export default index
