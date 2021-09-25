import React from 'react'

import InputBox from "../../module/input-box";
import PageHeader from "../../module/page-header";
import Button from "../../module/button";

import * as S from "./styles";

const index = () => {
  return (
    <S.Container>
      <PageHeader title="로그인" />
      <S.InputWrapper>
        <S.Label><span>📫 이메일</span></S.Label>
        <InputBox styleType="Simple" label="이메일을 입력하세요" />
      </S.InputWrapper>
      <S.InputWrapper>
        <S.Label><span>🔒 비밀번호</span></S.Label>
        <InputBox styleType="Password" label="비밀번호를 입력하세요" />
        <S.FindPasswordWrapper>
          <S.FindPasswordLabel>비밀번호 찾기</S.FindPasswordLabel>
        </S.FindPasswordWrapper>
      </S.InputWrapper>

      <S.ButtonWrapper>
        <Button width="100" height="40" label="로그인" />
      </S.ButtonWrapper>
    </S.Container>
  )
}

export default index;
