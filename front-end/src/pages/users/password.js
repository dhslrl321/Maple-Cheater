import { useState } from 'react'
import { useRouter } from "next/router";
import { useDispatch, useSelector } from "react-redux";

import ChangePassword from "../../component/section/change-password";
import useAxios from "../../hooks/use-axios";
import withAuthentication from "../../higher-order-component/with-authentication";
import { fetchChangePassword } from "../../services/user-service";
import { enableAlert } from "../../reducers/application";

const password = () => {
  const dispatch = useDispatch();
  const router = useRouter();
  const [inputs, setInputs] = useState({
    oldPassword: "",
    newPassword: "",
    newPasswordCheck: "",
  });

  const [isSamePassword, setIsSamePassword] = useState(false);

  const handleOnChange = (e) => {
    const { name, value } = e.target;

    setInputs({
      ...inputs,
      [name]: value
    });

    if (name === "newPassword" && inputs.newPasswordCheck !== value) {
      setIsSamePassword(true);
    }

    if (name === "newPasswordCheck" && inputs.newPassword !== value) {
      setIsSamePassword(true);
    }

    if (name === "newPasswordCheck" && inputs.newPassword === value) {
      setIsSamePassword(false);
    }
  };

  const { data, status } = useSelector(state => state.userReducer.user);

  const [state, refetch] = useAxios(
    () => fetchChangePassword(data.userId, inputs.oldPassword, inputs.newPassword),
    [data && data.userId, inputs.oldPassword, inputs.newPassword], true);

  const handleChangeClick = () => {
    refetch();
  }

  if (state.status === 204) {
    dispatch(enableAlert({
      title: "비밀번호 변경 성공",
      message: "비밀번호 변경에 성공하였습니다.",
      severity: "success"
    }));
    router.push("/");
  }

  if (state.status === 401 || state.status === 403) {
    dispatch(enableAlert({
      title: "인증 정보 불일치",
      message: "인증 정보가 올바르지 않습니다. 비밀번호를 확인해주세요"
    }));
    router.push("/");
  }

  if (state.status === 400) {
    dispatch(enableAlert({
      title: "입력 값 오류",
      message: "입력 값에 오류가 존재합니다."
    }));
    router.push("/");
  }

  const buttonLock = (inputs.oldPassword === ""
    || inputs.newPassword === ""
    || inputs.newPasswordCheck === ""
    || isSamePassword
    || state.loading ? true : false);

  return <ChangePassword
    inputs={inputs}
    handleOnChange={handleOnChange}
    handleChangeClick={handleChangeClick}
    isSamePassword={isSamePassword}
    loading={state.loading}
    lock={buttonLock} />
}

export default withAuthentication(password);
