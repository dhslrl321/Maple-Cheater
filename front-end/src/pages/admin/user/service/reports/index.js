import React, { useEffect } from 'react'
import { useDispatch } from "react-redux";
import { useRouter } from "next/router";

import useAxios from "../../../../../hooks/use-axios";
import withAuthentication from "../../../../../higher-order-component/with-authentication";
import { fetchAllReports } from "../../../../../services/admin-service";

import { enableAlert } from "../../../../../reducers/application";
import AdminReport from "../../../../../component/section/my-report";

const Reports = () => {
  const router = useRouter();
  const dispatch = useDispatch();

  const [reportListState, refetch] = useAxios(
    () => fetchAllReports(), [], true);

  const { loading, data, status } = reportListState;

  useEffect(() => {
    refetch();
  }, []);

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

  return <AdminReport
    isAdmin
    reports={report}
    loading={loading}
    status={status} />
}

export default withAuthentication(Reports);
