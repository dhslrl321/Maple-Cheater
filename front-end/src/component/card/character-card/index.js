import React from 'react'

import * as S from "./styles";

const index = ({ title, paragraph, source, isLeft }) => {
  return (
    <S.Container>
      <S.CardWrapper isLeft={isLeft}>
        <S.CardImageWrapper>
          <S.CardImage isLeft={isLeft} src={source} />
        </S.CardImageWrapper>
        <S.CardTextWrapper isLeft={isLeft}>
          <S.CardTitle>{title}</S.CardTitle>
          <S.CardParagraph>{paragraph}</S.CardParagraph>
        </S.CardTextWrapper>
      </S.CardWrapper>
    </S.Container>
  )
}

export default index
