import React from 'react'

import * as S from "./styles";

const index = ({ title, subtitle }) => {
  return (
    <S.Container>
      <S.TitleWrapper>
        <S.Title>{title}</S.Title>
      </S.TitleWrapper>
      <S.TitleWrapper>
        <S.Subtitle>{subtitle}</S.Subtitle>
      </S.TitleWrapper>
    </S.Container>
  )
}

export default index;
