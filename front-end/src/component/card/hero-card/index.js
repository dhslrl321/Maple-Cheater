import React from 'react'

import * as S from "./style";

const HeroCard = ({ title, description, isLeft, src }) => {

  return (
    <S.Container isLeft={isLeft}>
      <S.TextWrapper>
        <S.Title>{title}</S.Title>
        <S.Description>{description}</S.Description>
      </S.TextWrapper>
      <S.ImageWrapper>
        <S.Image src={src} />
      </S.ImageWrapper>
    </S.Container>
  )
}

export default HeroCard;
