import { useSelector } from "react-redux";

import useMediaQuery from '@mui/material/useMediaQuery';

import * as S from "./styles";

import Chip from '@mui/material/Chip';
import Stack from '@mui/material/Stack';

import CheaterDetailTable from "../../group/cheater-detail-table";
import Loading from "../loading";

const CheaterInfo = () => {
  const isSmall = useMediaQuery("(max-width: 567px)");

  const { nickname } = useSelector(state => state.cheaterReducer.search);
  const { loading, status, data } = useSelector(state => state.cheaterReducer.cheater);

  const cheaterManual = (
    <S.Manual>거래를 진행중인 상대방의 <span style={{ fontWeight: "bold" }}>캐릭터 이름</span>을 입력하면 해당 캐릭터로 신고된 이력에 대해서 파악할 수 있습니다.</S.Manual>
  )

  const detailManual = (
    <S.Manual>검색한 캐릭터의 <span style={{ fontWeight: "bold" }}>사기 이력</span>에 대해서 확인할 수 있습니다.</S.Manual>
  )

  const cheater = (
    <S.CheaterWrapper>
      <S.NicknameWrapper>
        <Chip label={nickname} color="default" size={isSmall ? "small" : "medium"} />
      </S.NicknameWrapper>
      <Stack direction="row" spacing={1} flexDirection={isSmall ? "column" : "row"} >
        <Chip label={`현금 거래`} color="error" variant="outlined" size={isSmall ? "small" : "medium"} />
        <Chip label={`주문서 거래`} color="warning" variant="outlined" size={isSmall ? "small" : "medium"} />
        <Chip label={`사냥터 비매너`} color="success" variant="outlined" size={isSmall ? "small" : "medium"} />
      </Stack>
    </S.CheaterWrapper>
  );

  if (status === 401 || status === 400) {
    dispatch(showAlert({
      title: "인증 오류",
      message: "비정상적인 접근이 탐지되었습니다. 누적되면 제제를 당할 수 있습니다.",
      severity: "error"
    }))
    router.push("/");
    return <></>;
  }

  const detail = (
    <CheaterDetailTable
      isSmall={isSmall}
      cheaterNickname={nickname}
      histories={status === 200 ? data.cheaterReportHistories : []} />);

  return (
    <S.Container>
      <S.Title>검색 결과</S.Title>
      <S.CheaterCard>
        {loading ? (
          <Loading />
        ) : (
            status === 200 || status === 202 ? (
              cheater
            ) : (
                cheaterManual
              )
          )}

      </S.CheaterCard>
      <S.Title>확인된 신고 이력</S.Title>
      <S.CheaterDetailWrapper>
        {loading ? (
          <Loading />
        ) : (
            status === 200 || status === 202 ? (
              detail
            ) : (
                detailManual
              )
          )}
      </S.CheaterDetailWrapper>
    </S.Container>
  )
}

export default CheaterInfo
