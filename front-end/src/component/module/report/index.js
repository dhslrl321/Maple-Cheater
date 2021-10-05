import { useState } from 'react'

import * as S from "./styles";

import Divider from '@mui/material/Divider';

import Loading from "../loading";
import EvidenceImage from "../../modal-template/evidence-image";
import Modal from "../../modal-template/modal";

import { convertToClip } from "../../../utils/converter";

const Report = ({ reporter, report, loading, images }) => {

  const { reportId, cheaterNickname, registeredAt, cheatingType, cheaterServer, cheatingDatetime, situation, status } = report;

  const [open, setOpen] = useState(false);
  const [modalImage, setModalImage] = useState();

  const handleOnModalOpen = (imageUrl) => {
    setModalImage(imageUrl);
    setOpen(true);
  };
  const handleOnModalClose = () => {
    setOpen(false);
  };

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
                  {images && images.map(image => <S.Image onClick={() => handleOnModalOpen(image.imageUrl)} src={image.imageUrl} />)}
                </S.ImageWrapper>
              </S.ImageContent>
            </S.ContentWrapper>
            <Modal
              open={open}
              image
              handleOnModalClose={handleOnModalClose}>
              <EvidenceImage imageUrl={modalImage} />
            </Modal>
          </>
        )}
    </S.Container>
  )
}

export default Report;
