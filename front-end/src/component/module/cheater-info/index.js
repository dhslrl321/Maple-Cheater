import React from 'react'

import useMediaQuery from '@mui/material/useMediaQuery';

import * as S from "./styles";

import Chip from '@mui/material/Chip';
import Stack from '@mui/material/Stack';

import CheaterDetailTable from "../../group/cheater-detail-table";

const cheaterReportHistories = [
  { cheatingDatetime: "2021-10-01T20:24:37", situation: "영환불작", cheatingType: "주문서 거래" },
  { cheatingDatetime: "2021-10-01T20:24:37", situation: "물통 사기", cheatingType: "현금 거래" },
  { cheatingDatetime: "2021-10-01T20:24:37", situation: "리턴 스크롤 사기", cheatingType: "주문서 거래" },
  { cheatingDatetime: "2021-10-01T20:24:37", situation: "사냥 방해", cheatingType: "사냥터 비매너" },
  { cheatingDatetime: "2021-10-01T20:24:37", situation: "아르카나 자리 스틸", cheatingType: "사냥터 비매너" },
]

const CheaterInfo = ({ histories }) => {

  const isSmall = useMediaQuery("(max-width: 567px)");

  const cheaterManual = (
    <S.Manual>거래를 진행중인 상대방의 <span style={{ fontWeight: "bold" }}>캐릭터 이름</span>을 입력하면 해당 캐릭터로 신고된 이력에 대해서 파악할 수 있습니다.</S.Manual>
  )

  const detailManual = (
    <S.Manual>검색한 캐릭터의 <span style={{ fontWeight: "bold" }}>사기 이력</span>에 대해서 확인할 수 있습니다.</S.Manual>
  )

  const cheater = (
    <S.CheaterWrapper>
      <S.NicknameWrapper>
        <Chip label="CodeDeploy" color="default" size={isSmall ? "small" : "medium"} />
      </S.NicknameWrapper>
      <Stack direction="row" spacing={1} flexDirection={isSmall ? "column" : "row"} >
        <Chip label={`현금 거래 : ${1}`} color="error" variant="outlined" size={isSmall ? "small" : "medium"} />
        <Chip label={`주문서 거래 : ${2}`} color="warning" variant="outlined" size={isSmall ? "small" : "medium"} />
        <Chip label={`사냥터 비매너 : ${2}`} color="success" variant="outlined" size={isSmall ? "small" : "medium"} />
      </Stack>
    </S.CheaterWrapper>
  )

  const detail = (
    <CheaterDetailTable
      isSmall={isSmall}
      cheaterNickname={"CodeDeploy"}
      histories={cheaterReportHistories} />);

  return (
    <S.Container>
      <S.Title>검색 결과</S.Title>
      <S.CheaterCard>
        {true ? cheater : cheaterManual}
      </S.CheaterCard>
      <S.Title>세부 내역</S.Title>
      <S.CheaterDetailWrapper>
        {true ? detail : detailManual}
      </S.CheaterDetailWrapper>
    </S.Container>
  )
}

export default CheaterInfo
