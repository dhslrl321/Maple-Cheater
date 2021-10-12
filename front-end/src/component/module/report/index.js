import { useState } from 'react'
import { useRouter } from "next/router";
import { useDispatch } from "react-redux";

import * as S from "./styles";

import Divider from '@mui/material/Divider';

import Loading from "../loading";
import EvidenceImage from "../../modal-template/evidence-image";
import CheaterSubmit from "../../modal-template/cheater-submit";
import Modal from "../../modal-template/modal";
import Button from "../../module/button";

import useAxios from "../../../hooks/use-axios";
import { convertToClip } from "../../../utils/converter";
import { enableAlert } from "../../../reducers/application";
import { fetchUpdateReportStatus } from "../../../services/admin-service";

const Report = ({ isAdmin, reporter, report, loading, images }) => {
  const dispatch = useDispatch();
  const router = useRouter();
  const { reportId, cheaterNickname, registeredAt, cheatingType, cheaterServer, cheatingDatetime, situation, status } = report;

  const [open, setOpen] = useState(false);
  const [modalImage, setModalImage] = useState();

  const [submitModal, setSubmitModal] = useState(false);

  const handleOnModalOpen = (imageUrl) => {
    setModalImage(imageUrl);
    setOpen(true);
  };

  const handleOnModalClose = () => {
    setOpen(false);
  };

  const handleOnSubmitModalOpen = () => {
    setSubmitModal(true);
  };

  const handleOnSubmitModalClose = () => {
    setSubmitModal(false);
  };

  const [rejectReportState, rejectReport] = useAxios(() => fetchUpdateReportStatus(reportId, true), [reportId], true);

  const handleRejectClick = () => {
    rejectReport();
  }

  if (rejectReportState.status === 200) {
    router.reload();
  }

  if (rejectReportState.status === 400) {
    dispatch(enableAlert({
      title: "리포트의 status가 업데이트되지 않았습니다.",
      message: "존재하지 않는 reportId 입니다."
    }));
  }
  return (
    <S.Container>
      {loading ? (
        <Loading />
      ) : (
          <>
            <S.HeaderWrapper>
              <S.HeaderColumn>
                <div>{reportId}</div>
                <div>{cheatingType}</div>
              </S.HeaderColumn>
              <S.HeaderColumn>
                <div>{convertToClip(status)}</div>
                <div>{registeredAt && registeredAt.substr(0, 10)}</div>
              </S.HeaderColumn>
            </S.HeaderWrapper>
            <Divider />
            <S.ContentWrapper>
              <S.Content>
                <S.Title>상대 캐릭터 닉네임</S.Title>
                <S.Text>{cheaterNickname}</S.Text>
              </S.Content>
              <S.Content>
                <S.Title>서버</S.Title>
                <S.Text>{cheaterServer}</S.Text>
              </S.Content>
              <S.Content>
                <S.Title>거래 시간</S.Title>
                <S.Text>{cheatingDatetime && cheatingDatetime.substr(0, 10)}</S.Text>
              </S.Content>
              <S.Content>
                <S.Title>거래 종류</S.Title>
                <S.Text>{cheatingType}</S.Text>
              </S.Content>
              <S.Content>
                <S.Title>문제 상황</S.Title>
                <S.Text>{situation}</S.Text>
              </S.Content>
              <S.ImageContent>
                <S.Title>증거 이미지</S.Title>
                <S.ImageWrapper>
                  {images && images.map((image, index) => <S.Image key={index} onClick={() => handleOnModalOpen(image.imageUrl)} src={image.imageUrl} />)}
                </S.ImageWrapper>
              </S.ImageContent>
              {isAdmin && (
                <S.ButtonWrapper>
                  {status === "PENDING" ? (
                    <>
                      <Button handleOnClick={handleRejectClick} label="거부" width="60" height="44" />
                      <Button handleOnClick={handleOnSubmitModalOpen} label="등록" width="60" height="44" />
                    </>
                  ) : (
                      <span>이미 처리된 신고서입니다.</span>
                    )}

                </S.ButtonWrapper>
              )}
            </S.ContentWrapper>
            <Modal
              open={open}
              image
              handleOnModalClose={handleOnModalClose}>
              <EvidenceImage imageUrl={modalImage} />
            </Modal>
            <Modal
              open={submitModal}
              handleOnModalClose={handleOnSubmitModalClose}>
              <CheaterSubmit
                reporter={reporter}
                report={report} />
            </Modal>
          </>
        )}
    </S.Container>
  )
}

export default Report;
