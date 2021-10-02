import React from 'react'

import useMediaQuery from '@mui/material/useMediaQuery';

import * as S from "./styles";
import Chip from '@mui/material/Chip';
import Stack from '@mui/material/Stack';

const CheaterInfo = () => {

  const isSmall = useMediaQuery("(max-width: 567px)");

  const manual = (
    <S.Manual>거래를 진행중인 상대방의 캐릭터 이름이나 계좌번호를 입력하면 해당 캐릭터와 연관된 캐릭터의 신고 정보에 대해서 파악할 수 있습니다.</S.Manual>
  )

  const cheater = (
    <S.CheaterWrapper>
      <S.NicknameWrapper>
        <Chip label="CodeDeploy" color="default" size={isSmall ? "small" : "medium"} />
      </S.NicknameWrapper>
      <Stack direction="row" spacing={1} flexDirection={isSmall ? "column" : "row"} >
        <Chip label={`현금 거래 : ${1}`} color="error" variant="outlined" size={isSmall ? "small" : "medium"} />
        <Chip label={`주문서 거래 : ${1}`} color="warning" variant="outlined" size={isSmall ? "small" : "medium"} />
        <Chip label={`사냥터 비매너 : ${0}`} color="success" variant="outlined" size={isSmall ? "small" : "medium"} />
      </Stack>
    </S.CheaterWrapper>
  )

  return (
    <S.Container>
      <S.Title>검색 결과</S.Title>
      <S.CheaterCard>
        {cheater}
      </S.CheaterCard>
    </S.Container>
  )
}

export default CheaterInfo
