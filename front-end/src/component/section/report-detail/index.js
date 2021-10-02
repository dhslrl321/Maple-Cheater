import React from 'react'

import * as S from "./styles";

import PageHeader from "../../module/page-header";
import Report from "../../module/report";

const ReportDetail = ({ report }) => {
  return (
    <S.Container>
      <PageHeader title="신고 세부 정보" />
      <div> 뒤로 가기</div>
      <Report report={report} />
    </S.Container>
  )
}

export default ReportDetail
