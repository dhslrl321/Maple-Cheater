import React from 'react'

import * as S from "./styles";

import ReportTable from "../../group/report-table";

const ReportList = ({ reports }) => {
  return (
    <S.Container>
      <ReportTable reports={reports} />
    </S.Container>
  )
}

export default ReportList

