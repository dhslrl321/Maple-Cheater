import { useState } from "react";

import Register from "../component/section/register";

import useAxios from "../hooks/use-axios";
import { fetchEmailVerification } from "../services/auth-service";

const register = () => {

  const [inputs, setInputs] = useState({
    email: "",
    authCode: "",
    password: "",
    passwordCheck: "",
    nickname: ""
  });

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

  const [state, sendMail] = useAxios(() => fetchEmailVerification(inputs.email), [inputs.email], true);

  const { loading, data } = state;

  const handleAuthenticateAuthCode = () => {
    console.log("버튼 클릭됨");
  }

  return (
    <>
      <Register
        handleOnChange={handleOnChange}
        inputLock={inputLock}
        handleSendAuthCodeMail={sendMail}
        loading={loading}
        handleAuthenticateAuthCode={handleAuthenticateAuthCode}
        values={inputs} />
    </>
  )
}

export default register
