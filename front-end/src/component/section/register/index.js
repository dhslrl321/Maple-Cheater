import React, { useState } from 'react'

import InputBox from "../../module/input-box";
import SelectBox from "../../module/select-box";

import PageHeader from "../../module/page-header";
import Button from "../../module/button";

import * as S from './styles';

const index = ({
  handleOnChange,
  values,
  inputLock,
  handleSendMail,
  handleAuthenticateAuthCode,
  loading }) => {

  const { email, authCode, password, passwordCheck, nickname } = values;
  const { emailLock, authCodeLock } = inputLock;

  return (
    <S.Container>
      <PageHeader title="회원가입" subtitle="간단한 이메일 인증으로 빠르게 회원가입을 해보세요!" />
      <S.InputWrapper>
        <S.Label><span>📫 이메일</span></S.Label>
        <S.Column>
          <InputBox
            name="email"
            disabled={emailLock}
            handleOnChange={handleOnChange}
            value={email}
            styleType="Simple"
            label="이메일을 입력하세요" />
          <Button
            width="158"
            height="55"
            label="인증 번호 전송"
            disabled={emailLock}
            loading={loading}
            handleOnClick={handleSendMail} />
        </S.Column>
      </S.InputWrapper>
      <S.InputWrapper>
        <S.Label><span>📃 인증</span></S.Label>
        <S.Column>
          <InputBox
            styleType="Simple"
            disabled={authCodeLock}
            name="authCode"
            value={authCode}
            handleOnChange={handleOnChange}
            label="이메일 인증번호 6자리를 입력하세요" />
          <Button
            width="80"
            height="55"
            label="인증"
            handleOnClick={handleAuthenticateAuthCode} />
        </S.Column>
      </S.InputWrapper>

      <S.InputWrapper>
        <S.Label><span>🔒 비밀번호</span></S.Label>
        <InputBox
          styleType="Password"
          name="password"
          value={password}
          handleOnChange={handleOnChange}
          label="비밀번호를 입력하세요" />
      </S.InputWrapper>

      <S.InputWrapper>
        <S.Label><span>🔒 비밀번호 확인</span></S.Label>
        <InputBox
          styleType="Password"
          name="passwordCheck"
          value={passwordCheck}
          handleOnChange={handleOnChange}
          label="비밀번호를 재입력해주세요" />
      </S.InputWrapper>

      <S.InputWrapper>
        <S.Label><span>🏷 닉네임</span></S.Label>
        <InputBox
          styleType="Simple"
          name="nickname"
          value={nickname}
          handleOnChange={handleOnChange}
          label="닉네임을 입력하세요" />
      </S.InputWrapper>

      <S.ButtonWrapper>
        <Button width="100" height="40" label="회원가입" />
      </S.ButtonWrapper>
    </S.Container >
  )
}

export default index;
