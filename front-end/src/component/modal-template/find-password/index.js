import React from 'react'

import Typography from '@mui/material/Typography';

import InputBox from "../../module/input-box";
import Button from "../../module/button";

import * as S from "./styles";

const index = () => {
  return (
    <>
      <Typography id="modal-modal-title" variant="h6" component="h2">
        비밀번호 찾기
      </Typography>
      <S.InputWrapper>
        <S.Label>
          인증 번호를 받을 이메일을 입력해주세요
        </S.Label>
        <InputBox styleType="Simple" label="회원가입 시 등록한 이메일" />
        <S.SendButtonWrapper>
          <Button withoutMargin height={40} label="임시 비밀번호 전송" />
        </S.SendButtonWrapper>
      </S.InputWrapper>
    </>
  )
}

export default index
