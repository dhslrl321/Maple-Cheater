import { useState } from "react";
import { useRouter } from "next/router";

import { useSelector, useDispatch } from "react-redux";

import Login from "../component/section/login";
import AlertSelfClose from "../component/module/alert-self-close";
import Alert from "../component/module/alert";

import { getUser } from "../reducers/user";
import { emailValidator, emptyTextValidator } from "../utils/validator";
import * as Storage from "../utils/storage";

const LoginPage = () => {
  const { data, status } = useSelector(state => state.userReducer.user);

  const dispatch = useDispatch();
  const router = useRouter();

  const [alert, setAlert] = useState({
    open: false,
    title: "",
    message: ""
  });

  const handleAlertClose = () => {
    setAlert({
      ...alert,
      open: false
    });
  }

  const [inputs, setInputs] = useState({
    email: "",
    password: ""
  });

  const handleOnChange = (e) => {
    const { name, value } = e.target;
    setInputs({
      ...inputs,
      [name]: value
    });
  };

  const handleLoginClick = () => {
    const { email, password } = inputs;

    if (!emptyTextValidator(email) || !emptyTextValidator(password)) {
      setAlert({
        open: true,
        title: "입력 값 에러",
        message: "입력 폼 (이메일, 비밀번호) 을 모두 입력해주세요"
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

    const user = {
      email,
      password
    };

    dispatch(getUser(user));
  }

  if (status === 200) {
    const { userId, email, nickname, accessToken } = data;
    const responseUser = {
      userId,
      email,
      nickname
    };

    Storage.setUser(responseUser);

    Storage.setAccessToken(accessToken);
    router.push("/");
  }

  return (
    <>
      <Login
        handleOnChange={handleOnChange}
        handleLoginClick={handleLoginClick}
        inputs={inputs} />
      <Alert
        isOpen={alert.open}
        severity="error"
        handleAlertClose={handleAlertClose}
        title={alert.title}
        message={alert.message} />
      {(status === 403 && inputs.email !== "") && <AlertSelfClose
        severity="error"
        title="로그인 실패"
        message={data} />}
      {(status === 400 && inputs.email !== "") && <AlertSelfClose
        severity="error"
        title="로그인 실패"
        message={data} />}
    </>);
}

export default LoginPage
