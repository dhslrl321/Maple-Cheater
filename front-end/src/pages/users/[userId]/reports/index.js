import React, { useEffect } from 'react'
import { useRouter } from "next/router";

import * as Storage from "../../../../utils/storage";

import useAxios from "../../../../hooks/use-axios";
import withAuthentication from '../../../../higher-order-component/with-authentication';

import { fetchMyReportList } from "../../../../services/user-service";

import MyReport from "../../../../component/section/my-report";

const userId = () => {
  const router = useRouter();

  const accessToken = Storage.getAccessToken();
  const { userId } = router.query;
  const [reportListState, refetch] = useAxios(
    () => fetchMyReportList(accessToken, userId), [accessToken, userId], true);

  const { loading, data: stateData, status: stateStatus } = reportListState;

  useEffect(() => {
    refetch();
  }, [])

  const reports = [
    { reportId: 21, reporterNickname: "히어로123", status: "PENDING", cheatingType: "현금 거래", registeredAt: "2021-10-02T01:56:00" },
    { reportId: 51, reporterNickname: "히어로123", status: "PENDING", cheatingType: "현금 거래", registeredAt: "2021-10-02T01:56:00" },
    { reportId: 121, reporterNickname: "히어로123", status: "PENDING", cheatingType: "현금 거래", registeredAt: "2021-10-02T01:56:00" },
    { reportId: 451, reporterNickname: "히어로123", status: "REJECTED", cheatingType: "현금 거래", registeredAt: "2021-10-02T01:56:00" },
    { reportId: 6121, reporterNickname: "히어로123", status: "ACCEPTED", cheatingType: "현금 거래", registeredAt: "2021-10-02T01:56:00" },
    { reportId: 12311, reporterNickname: "히어로123", status: "ACCEPTED", cheatingType: "현금 거래", registeredAt: "2021-10-02T01:56:00" },
    { reportId: 21121, reporterNickname: "히어로123", status: "ACCEPTED", cheatingType: "현금 거래", registeredAt: "2021-10-02T01:56:00" },
  ]

  return <MyReport reports={reports} />
}

export default withAuthentication(userId);

export const getServerSideProps = async () => {
  return {
    props: {}
  }
}