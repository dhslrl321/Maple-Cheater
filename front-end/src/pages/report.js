import { useState } from 'react'
import { useRouter } from "next/router";
import { useDispatch, useSelector } from "react-redux";

import withAuthentication from "../higher-order-component/with-authentication";
import ReportTemplate from "../component/section/report-template";
import useAxios from "../hooks/use-axios";
import { fetchAddReport } from "../services/report-service";
import { enableAlert } from "../reducers/application";

const Report = () => {

  const router = useRouter();
  const dispatch = useDispatch();

  const [inputs, setInputs] = useState({
    nickname: "",
    situation: "",
    serverSelect: 0,
    cheatingTypeSelect: 0,
    yearSelect: 0,
    monthSelect: 0,
    daySelect: 0,
  });

  const [files, setFiles] = useState([]);

  const { data } = useSelector(state => state.userReducer.user);

  const [addReportState, addReport] = useAxios(
    () => fetchAddReport(data.userId, inputs, files), [inputs, data && data.userId, files], true);

  const { status, loading } = addReportState;

  const handleOnFileUpload = (files) => {
    setFiles([...files, files]);
  }

  const handleOnChange = (e) => {
    const { name, value } = e.target;
    setInputs({
      ...inputs,
      [name]: value
    });
  }

  const handleSubmitButtonClick = () => {
    addReport();
  }

  const buttonLock = (inputs.nickname === ""
    || inputs.situation === ""
    || inputs.serverSelect === 0
    || inputs.cheatingType === 0
    || inputs.yearSelect === 0
    || inputs.monthSelect === 0
    || inputs.daySelect === 0)
    || files.length === 1
    || loading ? true : false;

  if (status === 400) {
    dispatch(enableAlert({
      title: "입력 값 에러",
      message: "입력 값이 올바르지 않습니다."
    }));
    router.push("/");
  }

  if (status === 500) {
    dispatch(enableAlert({
      title: "서버 에러",
      message: "현재 서버에 문제가 발생하였습니다 빠른 시일 내에 조취를 취하도록 하겠습니다."
    }));
    router.push("/");
  }

  if (status === 201 && data !== null) {
    router.push(`/users/${data.userId}/reports`);
  }

  return <ReportTemplate
    values={inputs}
    loading={loading}
    buttonLock={buttonLock}
    handleOnFileUpload={handleOnFileUpload}
    handleOnChange={handleOnChange}
    handleSubmitButtonClick={handleSubmitButtonClick} />
}

export default Report;
