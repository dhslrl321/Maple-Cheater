import React, { useState, useEffect } from 'react'
import { useSelector } from "react-redux";
import Link from "next/link";

import * as S from "./styles";

import Avatar from "../avatar";
import Divider from '@mui/material/Divider';

import * as Storage from "../../../utils/storage";

const Dropdown = ({ dropdown }) => {

  const [user, setUser] = useState({
    email: "",
    nickname: ""
  });

  useEffect(() => {
    const storageData = Storage.getUser();

    const email = storageData && storageData.email;
    const nickname = storageData && storageData.nickname;

    if (!email && !nickname) {
      setUser({
        email,
        nickname
      })
    }
  }, [])

  return (
    <S.Container dropdown={dropdown}>
      {user && (
        <S.UserProfile>
          <S.AvatarWrapper>
            <Avatar />
          </S.AvatarWrapper>
          <S.Nickname>
            {user.nickname}
          </S.Nickname>
          <S.Email>
            {user.email}
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
