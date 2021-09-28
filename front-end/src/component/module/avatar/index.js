import React from 'react'

import Avatar from '@mui/material/Avatar';

import * as S from "./styles";

import { FaAngleDown } from "react-icons/fa"

const index = ({ isNavigation }) => {
  return (
    <S.Container>
      <S.AvatarWrapper>
        <Avatar
          alt="프로필 이미지"
          src="/newbie.png"
          sx={{ width: 36, height: 36 }} />
      </S.AvatarWrapper>
      {isNavigation && (
        <S.IconWrapper>
          <FaAngleDown />
        </S.IconWrapper>
      )}
    </S.Container>
  )

}

export default index
