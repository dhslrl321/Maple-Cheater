import React from 'react'

import * as S from "./styles";

import PageHeader from "../../module/page-header";
import ReportList from "../../module/report-list";

const MyReport = ({ reports }) => {
  return (
    <S.Container>
      <PageHeader title="내 신고 이력" />
      <ReportList reports={reports} />
    </S.Container>
  )
}

export default MyReport
