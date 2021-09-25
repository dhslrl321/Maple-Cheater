import React from 'react'

import * as S from "./styles";

const index = ({ handleOnClick, label, width, height, bold }) => {
  return (
    <S.Button
      width={width}
      height={height}
      bold={bold}
    >
      {label}
    </S.Button>
  )
}

export default index
