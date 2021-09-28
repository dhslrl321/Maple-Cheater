import React from 'react'
import { useSelector } from "react-redux";
import Link from "next/link";

import * as S from "./styles";

import Avatar from "../avatar";
import Divider from '@mui/material/Divider';

const Dropdown = ({ dropdown }) => {

  const { data, status } = useSelector(state => state.userReducer.user);

  return (
    <S.Container dropdown={dropdown}>
      {(status !== 401 && data !== null) && (
        <S.UserProfile>
          <S.AvatarWrapper>
            <Avatar />
          </S.AvatarWrapper>
          <S.Nickname>
            {data.nickname}
          </S.Nickname>
          <S.Email>
            {data.email}
          </S.Email>
          <Divider style={{ width: "100%" }} />
        </S.UserProfile>
      )}

      <S.LinkList>
        <Link href="/mypage">내 신고 목록</Link>
      </S.LinkList>
      <S.LinkList>
        <Link href="/user/password">비밀번호 변경</Link>
      </S.LinkList>
      <S.LinkList>
        <Link href="/logout">로그아웃</Link>
      </S.LinkList>
    </S.Container>
  )
};

export default Dropdown;
