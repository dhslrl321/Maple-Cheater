import React from 'react'

import * as S from "./styles";

const Hero = () => {
  return (
    <S.Container>
      <S.AspirationWrapper>
        <S.SmallText>입금하고 오니까 사라진 캐릭터</S.SmallText>
        <S.BigText>"캐릭터를 찾을 수 없습니다."</S.BigText>
        <S.SmallText>항상 사기만 당하셨죠?</S.SmallText>
        <S.BigText>당신의 메소 제가 지켜드릴게요</S.BigText>
      </S.AspirationWrapper>
    </S.Container>
  )
}

export default Hero
