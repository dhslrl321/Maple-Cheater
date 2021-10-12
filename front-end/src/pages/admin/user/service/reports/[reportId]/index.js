import React, { useEffect } from 'react'
import { useDispatch } from "react-redux";
import { useRouter } from "next/router";

import ReportDetail from "../../../../../../component/section/report-detail";

import withAuthentication from "../../../../../../higher-order-component/with-authentication";
import useAxios from "../../../../../../hooks/use-axios";
import { fetchReportById } from "../../../../../../services/admin-service";
import { fetchEvidenceByReportId } from "../../../../../../services/evidence-service";
import { enableAlert } from "../../../../../../reducers/application";

const reportId = () => {

  const router = useRouter();
  const dispatch = useDispatch();

  const { reportId } = router.query;
  const [reportListState, refetch] = useAxios(
    () => fetchReportById(reportId), [reportId], true);

  const [evidenceState, evidenceRefetch] = useAxios(
    () => fetchEvidenceByReportId(reportId), [reportId], true);

  const { loading, data, status } = reportListState;
  const { data: evidenceData, status: evidenceStatus } = evidenceState;

  useEffect(() => {
    refetch();
    evidenceRefetch();
  }, []);

  if (status === 401 || status === 400 || status === 403) {
    router.push("/");
    dispatch(enableAlert({
      title: "인증 오류",
      message: "비정상적인 접근이 탐지되었습니다. 누적되면 제제를 당할 수 있습니다.",
      severity: "error"
    }))
  }

  return <ReportDetail isAdmin report={status === 200 && data} images={evidenceStatus === 200 && evidenceData} loading={loading} />
}

export default withAuthentication(reportId);

export const getServerSideProps = async () => {
  return {
    props: {}
  }
}