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

  return loading ? (
    <S.Button
      onClick={handleOnClick}
      width={width}
      height={height}
      bold={bold}
      disabled={disabled}
      withoutMargin={withoutMargin}
    >
      <CircularProgress color="white" size={20} />
    </S.Button>
  ) : (
      <S.Button
        onClick={handleOnClick}
        width={width}
        height={height}
        bold={bold}
        disabled={disabled}
        withoutMargin={withoutMargin}
      >
        {label}
      </S.Button>
    );
}

export default index
