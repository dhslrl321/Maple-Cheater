import React, { useEffect } from 'react'
import { useDispatch } from "react-redux";
import { useRouter } from "next/router";

import ReportDetail from "../../../../../component/section/report-detail";

import withAuthentication from "../../../../../higher-order-component/with-authentication";
import * as Storage from "../../../../../utils/storage";
import useAxios from "../../../../../hooks/use-axios";
import { fetchMyReportDetail } from "../../../../../services/user-service";
import { enableAlert } from "../../../../../reducers/application";

const reportId = () => {

  const router = useRouter();
  const dispatch = useDispatch();

  const accessToken = Storage.getAccessToken();

  const { userId, reportId } = router.query;
  const [reportListState, refetch] = useAxios(
    () => fetchMyReportDetail(accessToken, userId, reportId), [accessToken, userId], true);

  const { loading, data, status } = reportListState;

  useEffect(() => {
    refetch();
  }, []);

  if (status === 401 || status === 400) {
    router.push("/");
    dispatch(enableAlert({
      title: "인증 오류",
      message: "비정상적인 접근이 탐지되었습니다. 누적되면 제제를 당할 수 있습니다.",
      severity: "error"
    }))
  }

  return <ReportDetail report={status === 200 && data} />
}

export default withAuthentication(reportId);

export const getServerSideProps = async () => {
  return {
    props: {}
  }
}