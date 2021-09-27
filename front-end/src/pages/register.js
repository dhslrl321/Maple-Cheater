import React, { useState } from "react";
import { useRouter } from "next/router";

import Register from "../component/section/register";
import Alert from "../component/module/alert";
import AlertSelfClose from "../component/module/alert-self-close";

import useAxios from "../hooks/use-axios";
import { fetchEmailVerification, fetchAuthenticateAuthCode } from "../services/auth-service";
import { fetchRegister } from "../services/user-service";

import { emailValidator } from "../utils/validator";


const register = () => {
  const router = useRouter();
  const [isSamePassword, setIsSamePassword] = useState(false);

  const [inputs, setInputs] = useState({
    email: "",
    authCode: "",
    password: "",
    passwordCheck: "",
    nickname: ""
  });

  const [alert, setAlert] = useState({
    open: false,
    title: "",
    message: ""
  });


  const handleOnChange = (e) => {
    const { name, value } = e.target;

    setInputs({
      ...inputs,
      [name]: value
    });

    if (name === "password" && inputs.passwordCheck !== value) {
      setIsSamePassword(true);
    }

    if (name === "passwordCheck" && inputs.password !== value) {
      setIsSamePassword(true);
    }

    if (name === "passwordCheck" && inputs.password === value) {
      setIsSamePassword(false);
    }
  };

  const handleAlertClose = () => {
    setAlert({
      ...alert,
      open: false
    });
  }

  const [sendEmailState, sendMail] = useAxios(
    () => fetchEmailVerification(inputs.email), [inputs.email], true);

  const [authenticateAuthCodeState, authenticateAuthCode] = useAxios(
    () => fetchAuthenticateAuthCode(inputs.email, inputs.authCode), [inputs.email, inputs.authCode], true);

  const user = {
    email: inputs.email,
    password: inputs.password,
    nickname: inputs.nickname
  };

  const [registerState, register] = useAxios(() => fetchRegister(user), [], true);

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

  const {
    data: registerData,
    loading: registerLoading,
    status: registerStatus
  } = registerState;

  const handleRegisterClick = () => {
    const { email, password, passwordCheck, nickname } = inputs;
    const { emailLock, authCodeLock } = inputLock;
    if (email === "" || password === "" || nickname === "") {
      setAlert({
        open: true,
        title: "입력 값 에러",
        message: "입력 폼 (이메일, 비밀번호, 비밀번호 확인, 닉네임) 을 모두 입력해주세요"
      });
      return;
    }

    if (!emailValidator(email)) {
      setAlert({
        open: true,
        title: "입력 값 에러",
        message: "이메일 형식에 맞게 입력해주세요",
      });
      return;
    }

    if (password !== passwordCheck) {
      setAlert({
        open: true,
        title: "비밀번호 불일치",
        message: "입력하신 비밀번호와 비밀번호 확인이 서로 일치하지 않습니다."
      });
      return;
    }

    register();
  }

  const handleSendMailClick = () => {
    const { email } = inputs;
    if (email === "") {
      setAlert({
        open: true,
        title: "입력 값이 존재하지 않음",
        message: "이메일을 입력하세요!"
      });
      return;
    }

    if (!emailValidator(email)) {
      setAlert({
        open: true,
        title: "입력 값 에러",
        message: "이메일 형식에 맞게 입력해주세요",
      });
      return;
    }

    sendMail();
  }

  const handleAuthenticateAuthCodeClick = () => {
    const { email, authCode } = inputs;
    if (email === "" || authCode === "") {
      setAlert({
        open: true,
        title: "입력 값이 존재하지 않음",
        message: "이메일과 인증 코드를 모두 입력하세요!"
      });
      return;
    }

    authenticateAuthCode();
  }

  const loading = {
    sendEmailLoading,
    authenticateAuthCodeLoading
  };

  const inputLock = {
    emailLock: sendEmailStatus === 204 || sendEmailLoading ? true : false,
    authCodeLock: authenticateAuthCodeStatus === 204 ? true : false,
  };

  if (registerStatus === 201) {
    router.push("/");
  }


  const isRegisterOpen = (inputLock.emailLock && inputLock.authCodeLock) ? false : true;
  return (
    <>
      <Register
        handleOnChange={handleOnChange}
        inputLock={inputLock}
        handleSendMailClick={handleSendMailClick}
        handleAuthenticateAuthCodeClick={handleAuthenticateAuthCodeClick}
        handleRegisterClick={handleRegisterClick}
        isSamePassword={isSamePassword}
        isRegisterOpen={isRegisterOpen}
        values={inputs}
        loading={loading} />
      <Alert
        isOpen={alert.open}
        severity="error"
        handleAlertClose={handleAlertClose}
        title={alert.title}
        message={alert.message} />

      {sendEmailStatus === 400 && <AlertSelfClose
        severity="error"
        title="인증 오류"
        message={sendEmailData} />}

      {authenticateAuthCodeStatus === 403 && <AlertSelfClose
        severity="error"
        title="인증 코드 에러"
        message={authenticateAuthCodeData} />}

      {authenticateAuthCodeStatus === 400 && <AlertSelfClose
        severity="error"
        title="인증 에러"
        message="인증 정보(이메일 형식이 아닌 경우 혹은 인증 메일을 보내지 않은 이메일)가 유효하지 않습니다." />}
    </>
  )
}

export default register;
