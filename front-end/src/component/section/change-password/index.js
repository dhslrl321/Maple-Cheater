import React from 'react'

import * as S from "./styles";

import InputBox from "../../module/input-box";
import Button from "../../module/button";

const ChangePassword = ({ inputs, handleOnChange, handleChangeClick, isSamePassword, loading, lock }) => {

  const { oldPassword, newPassword, newPasswordCheck } = inputs;

  return (
    <S.Container>
      <S.WhiteBackground>
        <S.Title>비밀번호 변경하기</S.Title>
        <S.InputWrapper>
          <S.Label>현재 비밀번호</S.Label>
          <InputBox
            name="oldPassword"
            value={oldPassword}
            handleOnChange={handleOnChange}
            styleType="Password"
            label="현재 비밀번호" />
        </S.InputWrapper>

        <S.InputWrapper>
          <S.Label>새로운 비밀번호</S.Label>
          <InputBox
            name="newPassword"
            value={newPassword}
            handleOnChange={handleOnChange}
            error={isSamePassword}
            styleType="Password"
            label="새로운 비밀번호" />
        </S.InputWrapper>

        <S.InputWrapper>
          <S.Label>새로운 비밀번호 확인</S.Label>
          <InputBox
            name="newPasswordCheck"
            value={newPasswordCheck}
            handleOnChange={handleOnChange}
            error={isSamePassword}
            styleType="Password"
            label="새로운 비밀번호 확인" />
        </S.InputWrapper>
        <S.ButtonWrapper>
          <Button
            loading={loading}
            disabled={lock}
            handleOnClick={handleChangeClick}
            label="변경하기"
            width="80"
            height="44" />
        </S.ButtonWrapper>
      </S.WhiteBackground>
    </S.Container>
  )
}

export default ChangePassword
