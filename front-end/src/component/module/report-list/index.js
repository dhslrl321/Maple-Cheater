import React from 'react'

import * as S from "./styles";

import ReportTable from "../../group/report-table";
import Loading from "../loading";

const ReportList = ({ reports, loading, status }) => {
  return (
    <S.Container>
      {loading ? <Loading /> : <ReportTable reports={reports} />}
    </S.Container>
  )
}

export default ReportList

