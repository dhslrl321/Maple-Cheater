import React, { useState } from "react";

import Register from "../component/section/register";
import Alert from "../component/module/alert";

import useAxios from "../hooks/use-axios";
import { fetchEmailVerification, fetchAuthenticateAuthCode } from "../services/auth-service";


const register = () => {

  let alert;

  const [inputs, setInputs] = useState({
    email: "",
    authCode: "",
    password: "",
    passwordCheck: "",
    nickname: ""
  });

  const [alertOpen, setAlertOpen] = useState(false);

  const [inputLock, setInputLock] = useState({
    emailLock: false,
    authCodeLock: false,
  });

  const handleOnChange = (e) => {
    const { name, value } = e.target;
    setInputs({
      ...inputs,
      [name]: value
    })
  };

  const [sendEmailState, sendMail] = useAxios(
    () => fetchEmailVerification(inputs.email), [inputs.email], true);

  const [authenticateAuthCodeState, authenticateAuthCode] = useAxios(
    () => fetchAuthenticateAuthCode(inputs.email, inputs.authCode), [inputs.email, inputs.authCode], true);

  const {
    data: sendEmailData,
    loading: sendEmailLoading,
    error: sendEmailError,
    status: sendEmailStatus } = sendEmailState;

  const {
    data: authenticateAuthCodeData,
    loading: authenticateAuthCodeLoading,
    error: authenticateAuthCodeError,
    status: authenticateAuthCodeStatus } = authenticateAuthCodeState;

  if (authenticateAuthCodeData) {
    console.log(authenticateAuthCodeData);
  }

  const handleSendMail = () => {
    if (inputs.email === "") {
      alert = <Alert
        severity="error"
        title="입력 값이 존재하지 않음"
        message="이메일을 입력해주세요" />
      return;
    }

    sendMail();
  };

  const handleAuthenticateAuthCode = () => {
    if (inputs.email === "" || inputs.authCode === "") {
      console.log("클릭됨")
      setAlertOpen(true);

    }
    authenticateAuthCode();
  }

  if (authenticateAuthCodeStatus === 403) {
    alert = <Alert
      severity="error"
      title="인증 코드 에러"
      message="인증 코드가 올바르지 않습니다. 이메일로 전송된 인증 메일을 다시 확인해주세요" />
  }

  const loading = {
    sendEmailLoading,
    authenticateAuthCodeLoading
  };

  return (
    <>
      <Register
        handleOnChange={handleOnChange}
        inputLock={inputLock}
        handleSendMail={handleSendMail}
        handleAuthenticateAuthCode={handleAuthenticateAuthCode}
        values={inputs}
        loading={loading} />
      <Alert
        title="인증 코드 에러"
        message="인증 코드가 올바르지 않습니다. 이메일로 전송된 인증 메일을 다시 확인해주세요" />
    </>
  )
}

export default React.memo(register);
