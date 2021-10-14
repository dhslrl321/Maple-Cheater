import React, { useEffect } from 'react'
import { useDispatch } from "react-redux";
import { useRouter } from "next/router";

import * as Storage from "../../../../utils/storage";

import useAxios from "../../../../hooks/use-axios";
import withAuthentication from '../../../../higher-order-component/with-authentication';

import { fetchMyReportList } from "../../../../services/user-service";
import { enableAlert } from "../../../../reducers/application";

import MyReport from "../../../../component/section/my-report";

const UserId = () => {
  const router = useRouter();
  const dispatch = useDispatch();

  const accessToken = Storage.getAccessToken();
  const { userId } = router.query;
  const [reportListState, refetch] = useAxios(
    () => fetchMyReportList(accessToken, userId), [accessToken, userId], true);

  const { loading, data, status } = reportListState;

  useEffect(() => {
    refetch();
  }, [])

  if (status === 401 || status === 400 || status === 403) {
    dispatch(enableAlert({
      title: "인증 오류",
      message: "비정상적인 접근이 탐지되었습니다. 누적되면 제제를 당할 수 있습니다.",
      severity: "error"
    }))
    router.push("/");
    return <></>;
  }

  const report = status === 200 ? data.content : [];

  return <MyReport
    reports={report}
    loading={loading}
    status={status} />
}

export default withAuthentication(UserId);

export const getServerSideProps = async () => {
  return {
    props: {}
  }
}