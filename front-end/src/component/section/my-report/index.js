import React from 'react'

import * as S from "./styles";

import PageHeader from "../../module/page-header";
import ReportList from "../../module/report-list";

const MyReport = ({ isAdmin, reports, loading }) => {

  if (isAdmin) {
    return (
      <S.Container>
        <PageHeader title="모든 신고 이력 확인하기" />
        <ReportList
          isAdmin
          reports={reports}
          loading={loading} />
      </S.Container>
    )
  }

  return (
    <S.Container>
      <PageHeader title="내 신고 이력" />
      <ReportList
        reports={reports}
        loading={loading} />
    </S.Container>
  )
}

export default MyReport
