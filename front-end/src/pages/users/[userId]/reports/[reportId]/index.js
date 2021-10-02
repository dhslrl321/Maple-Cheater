import React, { useEffect } from 'react'
import { useRouter } from "next/router";

import ReportDetail from "../../../../../component/section/report-detail";

import withAuthentication from "../../../../../higher-order-component/with-authentication";
import * as Storage from "../../../../../utils/storage";
import useAxios from "../../../../../hooks/use-axios";
import { fetchMyReportDetail } from "../../../../../services/user-service";



const data = {
  reportId: 1,
  registeredAt: "2021-10-02T01:56:00.928488",
  cheaterNickname: "CodeDeploy",
  cheaterServer: "스카니아",
  cheatingType: "현금 거래",
  cheatingDatetime: "2021-10-01T20:24:37",
  situation: "주문서 거래를 시도하다가 안된다고 그냥 막 던졌어요",
  status: "PENDING"
};

const reportId = () => {

  const router = useRouter();

  const accessToken = Storage.getAccessToken();

  const { userId, reportId } = router.query;
  const [reportListState, refetch] = useAxios(
    () => fetchMyReportDetail(accessToken, userId, reportId), [accessToken, userId], true);

  const { loading, data: stateData, status: stateStatus } = reportListState;

  useEffect(() => {
    refetch();
  }, []);

  return <ReportDetail report={data} />
}

export default withAuthentication(reportId);

export const getServerSideProps = async () => {
  return {
    props: {}
  }
}