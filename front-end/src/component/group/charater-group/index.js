import React from 'react'

import CharacterCard from "../../card/character-card";
import * as S from "./styles";

const index = () => {
  return (
    <S.Container>
      <CharacterCard
        title="유저대 유저의 신뢰 기반 거래에서 정보 기반 거래로"
        paragraph="Maple Cheater 는 사용자 참여형 커뮤니티 프로젝트로, 시청자의 제보로 사기 캐릭터를 검색하여 미리 사기를 방지할 수 있도록 도와주는 서비스 입니다."
        source="/lala.png"
        isLeft={true} />

      <CharacterCard
        title="간단한 신고 절차"
        paragraph="계좌 거래 및 영환불, 검환불, 프로텍트 실드 등 다양한 신뢰 기반 거래에서 사용되고 사기를 예방할 수 있습니다."
        source="/hero.png"
        isLeft={false} />

      <CharacterCard
        title="inspired by 더 치트"
        paragraph="Maple Cheater는 사기피해사례 검색의 대표 서비스 '더치트' 에서 많은 아이디어 영감을 받았습니다."
        source="/lala.png"
        isLeft={true} />
    </S.Container>
  )
}

export default index
