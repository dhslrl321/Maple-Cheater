import React, { useState } from 'react'

import InputBox from "../../module/input-box";
import PageHeader from "../../module/page-header";
import Button from "../../module/button";
import Modal from "../../modal-template/modal";

import FindPassword from "../../modal-template/find-password";

import * as S from "./styles";

const index = ({
  handleOnChange,
  handleLoginClick,
  inputs
}) => {

  const [open, setOpen] = useState(false);
  const { email, password } = inputs;
  const handleOnModalOpen = () => {
    setOpen(true);
  };
  const handleOnModalClose = () => {
    setOpen(false);
  };
  return (
    <S.Container>
      <PageHeader title="로그인" subtitle="신고 접수나 캐릭터 검색을 위해 로그인을 해주세요! 🙂" />
      <S.InputWrapper>
        <S.Label><span>📫 이메일</span></S.Label>
        <InputBox
          styleType="Simple"
          handleOnChange={handleOnChange}
          name="email"
          value={email}
          label="이메일을 입력하세요" />
      </S.InputWrapper>
      <S.InputWrapper>
        <S.Label><span>🔒 비밀번호</span></S.Label>
        <InputBox
          styleType="Password"
          name="password"
          handleOnChange={handleOnChange}
          value={password}
          label="비밀번호를 입력하세요" />
        <S.FindPasswordWrapper>
          <S.FindPasswordLabel onClick={handleOnModalOpen}>
            비밀번호 찾기
          </S.FindPasswordLabel>
          <Modal
            open={open}
            handleOnModalClose={handleOnModalClose}>
            <FindPassword />
          </Modal>
        </S.FindPasswordWrapper>
      </S.InputWrapper>

      <S.ButtonWrapper>
        <Button handleOnClick={handleLoginClick} width="100" height="40" label="로그인" />
      </S.ButtonWrapper>
    </S.Container>
  )
}

export default index;
