import React, { useState } from 'react'

import Alert from "@mui/material/Alert";
import Typography from '@mui/material/Typography';

import * as S from "./styles";

import Button from "../../module/button";


const ReportSubmit = () => {
  return (
    <>
      <Typography id="modal-modal-title" variant="h6" component="h2">
        감사합니다!
      </Typography>
      <S.InputWrapper>
        <S.Label>
          당신의 신고가 메이플월드에서 누군가에게는 도움의 손길이 되었습니다.
        </S.Label>
        <S.Label>
          신고가 이뤄지고 최대 48시간 이내에 담당 부서에서 확인이 이뤄집니다. <br />
          신고 서비스는 개인정보와 밀접한 사항이므로 신중히 검토되고 해당 신고 내용에 누락된 내용이 있거나 불충분한 증거라면 신고는 자동 취소, 삭제될 수 있음을 알려드립니다.
        </S.Label>
        <S.SendButtonWrapper>
          <Button
            handleOnClick={() => console.log("asdf")}
            withoutMargin
            width={150}
            height={40}
            loading={false}
            disabled={false || status === 204 ? true : false}
            label="피해 등록하기" />
        </S.SendButtonWrapper>
      </S.InputWrapper>
    </>
  )
}

export default ReportSubmit
