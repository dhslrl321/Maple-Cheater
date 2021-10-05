import React, { useState } from 'react'

import Alert from "@mui/material/Alert";
import Typography from '@mui/material/Typography';

import * as S from "./styles";

import InputBox from "../../module/input-box";
import Button from "../../module/button";

import useAxios from '../../../hooks/use-axios';
import { fetchSendTempPassword } from "../../../services/auth-service";


const FindPassword = () => {

  const [email, setEmail] = useState("");
  const [inputEmpty, setInputEmpty] = useState(false);

  const handleOnChage = (e) => {
    const { value } = e.target;
    setEmail(value);
  }

  const [state, findPassword] = useAxios(() => fetchSendTempPassword(email), [email], true);

  const handleSendTempPassword = () => {
    if (email === "") {
      setInputEmpty(true);
      return;
    }
    findPassword();
  }

  const { data, status, loading } = state;

  return (
    <>
      <Typography id="modal-modal-title" variant="h6" component="h2">
        비밀번호 찾기
      </Typography>
      <S.InputWrapper>
        <S.Label>
          인증 번호를 받을 이메일을 입력해주세요
        </S.Label>
        <InputBox
          styleType="Simple"
          value={email}
          handleOnChange={handleOnChage}
          error={inputEmpty}
          label="회원가입 시 등록한 이메일" />
        {status === 204 && (
          <S.ResultAlertWrapper show>
            <Alert severity="success">임시 비밀번호를 발송했습니다. 다시 로그인해주세요</Alert>
          </S.ResultAlertWrapper>
        )}
        {status === 400 && (
          <S.ResultAlertWrapper show>
            <Alert severity="error">{data}</Alert>
          </S.ResultAlertWrapper>
        )}
        <S.SendButtonWrapper>
          <Button
            handleOnClick={handleSendTempPassword}
            withoutMargin
            width={150}
            height={40}
            loading={loading}
            disabled={loading || status === 204 ? true : false}
            label="임시 비밀번호 전송" />
        </S.SendButtonWrapper>
      </S.InputWrapper>
    </>
  )
}

export default FindPassword
