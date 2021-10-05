import { useState } from 'react'
import { useSelector } from "react-redux";

import withAuthentication from "../higher-order-component/with-authentication";
import ReportTemplate from "../component/section/report-template";
import useAxios from "../hooks/use-axios";
import { fetchAddReport } from "../services/report-service";
import { fetchUploadImage } from "../services/evidence-service";

const report = () => {

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
    () => fetchAddReport(inputs, data.userId), [inputs, data && data.userId], true);

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
    console.log(data.userId);
    addReport();
  }

  const buttonLock = (inputs.nickname === ""
    || inputs.situation === ""
    || inputs.serverSelect === 0
    || inputs.cheatingType === 0
    || inputs.yearSelect === 0
    || inputs.monthSelect === 0
    || inputs.daySelect === 0) ? true : false;

  return <ReportTemplate
    values={inputs}
    buttonLock={buttonLock}
    handleOnFileUpload={handleOnFileUpload}
    handleOnChange={handleOnChange}
    handleSubmitButtonClick={handleSubmitButtonClick} />
}

export default withAuthentication(report);
