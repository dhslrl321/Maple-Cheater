import { useSelector } from "react-redux";
import Link from "next/link";

import * as S from "./styles";

import PageHeader from "../../module/page-header";
import Report from "../../module/report";
import { FaAngleLeft } from "react-icons/fa";

const ReportDetail = ({ isAdmin, images, report, loading }) => {

  const { data } = useSelector(state => state.userReducer.user);

  if (isAdmin) {
    return (
      <S.Container>
        <PageHeader title="신고 세부 정보" />
        <Link href={data ? `/admin/user/service/reports` : `/`}><S.GoBack ><FaAngleLeft /> 뒤로 가기</S.GoBack></Link>
        <Report isAdmin reporter={data && data.nickname} images={images} report={report} loading={loading} />
      </S.Container>
    )
  }

  return (
    <S.Container>
      <PageHeader title="신고 세부 정보" />
      <Link href={data ? `/users/${data.userId}/reports` : `/`}><S.GoBack ><FaAngleLeft /> 뒤로 가기</S.GoBack></Link>
      <Report reporter={data && data.nickname} images={images} report={report} loading={loading} />
    </S.Container>
  )
}

export default ReportDetail
