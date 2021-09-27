import React from 'react'
import Link from "next/link";

import * as S from "./styles";

const Dropdown = ({ dropdown }) => {
  return (
    <S.Container dropdown={dropdown}>
      <S.LinkWrapper>
        <Link href="/mypage">내 신고 목록</Link>
      </S.LinkWrapper>
      <S.LinkWrapper>
        <Link href="/user/password">비밀번호 변경</Link>
      </S.LinkWrapper>
      <S.LinkWrapper>
        <Link href="/logout">로그아웃</Link>
      </S.LinkWrapper>
    </S.Container>
  )
};

export default Dropdown;
