import React from 'react'

import CharacterCard from "../../card/character-card";
import * as S from "./styles";

const index = () => {
  return (
    <S.Container>

      <S.Header>
        Maple-Cheater 는?
    </S.Header>

      <CharacterCard
        title="신뢰 기반 거래에서 정보 기반 거래로"
        paragraph="Maple Cheater 는 사용자 참여형 커뮤니티 프로젝트로, 사용자의 제보로 사기유저를 검색하여 사기를 방지할 수 있도록 돕는 서비스 입니다."
        source="/lala.png"
        isLeft={true} />

      <CharacterCard
        title="간단한 신고 및 검색"
        paragraph="계좌 거래 및 영환불, 검환불, 프로텍트 실드 등 다양한 신뢰 기반 거래에서 사용되고 사기를 예방할 수 있습니다."
        source="/hero.png"
        isLeft={false} />

      <CharacterCard
        title="사냥터 비매너 신고 서비스"
        paragraph="사냥터 비매너 신고제도를 운영하여 비매너 유저의 거래 신용도에 직접적으로 연관시켜 사냥터 비매너 문제에 기여"
        source="/lala.png"
        isLeft={true} />

      <CharacterCard
        title="inspired by 더 치트"
        paragraph="Maple Cheater는 사기피해사례 검색의 대표 서비스 '더치트' 에서 많은 아이디어 영감을 받았습니다."
        source="/hero.png"
        isLeft={false} />
    </S.Container>
  )
}

export default index
