import React from 'react'

import * as S from "./styles";

const index = ({ handleOnClick, label, width, height, bold, withoutMargin }) => {
  return (
    <S.Button
      onClick={handleOnClick}
      width={width}
      height={height}
      bold={bold}
      withoutMargin={withoutMargin}
    >
      {label}
    </S.Button>
  )
}

export default index
