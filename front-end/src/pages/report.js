import { useState } from 'react'

import withAuthentication from "../higher-order-component/with-authentication";
import ReportTemplate from "../component/section/report-template";

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

  const [files, setFiles] = useState({})

  const handleOnFileUpload = (files) => {
    setFiles({ files });
  }

  const handleOnChange = (e) => {
    const { name, value } = e.target;
    setInputs({
      ...inputs,
      [name]: value
    });
  }

  return <ReportTemplate
    values={inputs}
    handleOnFileUpload={handleOnFileUpload}
    handleOnChange={handleOnChange} />
}

export default withAuthentication(report);
