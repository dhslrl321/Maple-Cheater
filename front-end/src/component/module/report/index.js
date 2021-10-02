import React from 'react'

import * as S from "./styles";

const Report = ({ report }) => {
  return (
    <S.Container>
      <div>{report.cheaterNickname}</div>
      <div>{report.registeredAt}</div>
      <div>{report.cheaterServer}</div>
      <div>{report.cheatingType}</div>
      <div>{report.cheatingDatetime}</div>
      <div>{report.situation}</div>
      <div>{report.status}</div>
    </S.Container>
  )
}

export default Report;
