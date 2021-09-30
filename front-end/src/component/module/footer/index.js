import React from 'react'
import Link from "next/link";

import * as S from "./styles";

const index = () => {
  return (
    <S.Container>
      <S.TextWrapper>
        <S.Text>Copyright 2021 Maple-Cheater All right reserved</S.Text>
        <S.Text>Contact : maplecheater0@gmail.com</S.Text>
        <S.Text>본 서비스는 사용자들의 참여를 통한 커뮤니티 프로젝트입니다.</S.Text>
      </S.TextWrapper>
      <S.LinkWrapper>
        <S.Text>
          <Link href="/">커뮤니티 이용 정책</Link>
        </S.Text>
        <S.Text>
          <Link href="/">개인정보처리방침</Link>
        </S.Text>
      </S.LinkWrapper>
    </S.Container>
  )
}

export default index
