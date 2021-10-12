import React from 'react'

import * as S from "./styles";

import ReportTable from "../../group/report-table";
import Loading from "../loading";

const ReportList = ({ isAdmin, reports, loading }) => {
  return (
    <S.Container>
      {loading ? <Loading /> : <ReportTable isAdmin={isAdmin} reports={reports} />}
    </S.Container>
  )
}

export default ReportList

