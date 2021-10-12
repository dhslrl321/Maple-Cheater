import React from 'react'
import { useRouter } from "next/router";
import { useDispatch } from "react-redux";

import * as S from "./styles";

import Button from "../../module/button";
import Alert from "@mui/material/Alert";

import useAxios from "../../../hooks/use-axios";
import { convertTypeStringToId } from "../../../utils/converter";
import { fetchAddCheater, fetchUpdateReportStatus } from "../../../services/admin-service";
import { enableAlert } from "../../../reducers/application";

const CheaterSubmit = ({ reporter, report }) => {

  const dispatch = useDispatch();
  const router = useRouter();

  const { reportId, cheaterNickname, registeredAt, cheatingType, cheaterServer, cheatingDatetime, situation, status } = report;

  const cheatingTypeId = convertTypeStringToId("cheatingType", cheatingType);
  const ingameServerId = convertTypeStringToId("ingameServer", cheaterServer);

  const [addCheaterState, addCheater] = useAxios(() => fetchAddCheater(report, cheatingTypeId, ingameServerId), [report, cheatingTypeId, ingameServerId], true);
  const [acceptReportState, acceptReport] = useAxios(() => fetchUpdateReportStatus(reportId, false), [reportId], true);

  const handleSubmitClick = () => {
    addCheater();
  }

  if (addCheaterState.status === 400) {
    dispatch(enableAlert({
      title: "치터가 생성되지 않았습니다.",
      message: "어떠한 이유로 치터가 생성되지 않았습니다."
    }));
  }

  if (addCheaterState.status === 201) {
    acceptReport();
    router.reload();
  }

  if (acceptReportState.status === 400) {
    dispatch(enableAlert({
      title: "리포트의 status가 업데이트되지 않았습니다.",
      message: "어떠한 이유로 리포트의 status가 업데이트되지 않았습니다."
    }));
  }

  return (
    <div>
      <S.Column>
        <S.Title>신고 번호 : </S.Title><S.Content>{reportId}</S.Content>
      </S.Column>
      <S.Column>
        <S.Title>치터 닉네임 : </S.Title><S.Content>{cheaterNickname}</S.Content>
      </S.Column>
      <S.Column>
        <S.Title>치터 서버 : </S.Title><S.Content>{cheaterServer}</S.Content>
        <S.Title>id : </S.Title><S.Content>{ingameServerId}</S.Content>
      </S.Column>
      <S.Column>
        <S.Title>사기 시간 : </S.Title><S.Content>{cheatingDatetime}</S.Content>
      </S.Column>
      <S.Column>
        <S.Title>신고 종류 : </S.Title><S.Content>{cheatingType}</S.Content>
        <S.Title>id : </S.Title><S.Content>{cheatingTypeId}</S.Content>
      </S.Column>
      <S.Column>
        <S.Title>신고 문제 상황 : </S.Title><S.Content>{situation}</S.Content>
      </S.Column>
      <S.Column>
        <S.Title>신고 시간 : </S.Title><S.Content>{registeredAt}</S.Content>
      </S.Column>
      <Alert severity="info">확실히 확인하셨나요??</Alert>
      <S.ButtonWrapper>
        <Button handleOnClick={handleSubmitClick} label="등록하기" width="80" height="40" />
      </S.ButtonWrapper>
    </div>
  )
}

export default CheaterSubmit
