import React from 'react'

import * as S from "./styles";

import Alert from "@mui/material/Alert";
import { FaRegQuestionCircle } from "react-icons/fa";
import PageHeader from "../../module/page-header";
import Select from '../../module/select';
import InputBox from "../../module/input-box";
import FileDropzone from "../../module/file-dropzone";
import Modal from "../../modal-template/modal";
import ReportSubmit from "../../modal-template/report-submit";

import { server, cheatingType, year, month, day } from "../../../constants/select-value";
import Button from '../../module/button';


const ReportTemplate = ({
  values,
  loading,
  handleOnChange,
  handleOnFileUpload,
  handleSubmitButtonClick,
  buttonLock }) => {

  const { nickname, situation, serverSelect,
    cheatingTypeSelect,
    yearSelect,
    monthSelect,
    daySelect } = values;

  return (
    <S.Container>
      <Modal open={false}>
        <ReportSubmit />
      </Modal>
      <PageHeader
        title="피해 신고하기"
        subtitle="피해를 보신 사항에 대해서 신고하고 다른 사용자들에게 피해를 방지할 수 있도록 도와주세요" />
      <S.ReportWrapper>
        <S.InputColumn>
          <S.Label>서버</S.Label>
          <S.HelpText><FaRegQuestionCircle /><span>상대방의 인게임 서버를 입력해주세요</span></S.HelpText>
          <S.ServerSelectWrapper>
            <Select
              name="serverSelect"
              title="메이플 서버"
              label="서버를 선택하세요"
              value={serverSelect}
              handleOnChange={handleOnChange}
              menuItems={server} />
          </S.ServerSelectWrapper>
        </S.InputColumn>
        <S.InputColumn>
          <S.Label>상대 캐릭터 닉네임</S.Label>
          <S.HelpText><FaRegQuestionCircle /><span>상대방의 인게임 닉네임을 입력해주세요</span></S.HelpText>
          <S.NicknameWrapper>
            <InputBox
              styleType="Simple"
              name="nickname"
              value={nickname}
              handleOnChange={handleOnChange}
              label="상대 캐릭터 닉네임을 입력하세요" />
          </S.NicknameWrapper>
        </S.InputColumn>
        <S.InputColumn>
          <S.Label>신고 종류</S.Label>
          <S.HelpText><FaRegQuestionCircle /><span>신고 종류 (현금 거래, 주문서 거래, 사냥터 비매너) 를 선택해주세요</span></S.HelpText>
          <S.ServerSelectWrapper>
            <Select
              title="신고 종류"
              name="cheatingTypeSelect"
              label="서버를 선택하세요"
              value={cheatingTypeSelect}
              handleOnChange={handleOnChange}
              menuItems={cheatingType} />
          </S.ServerSelectWrapper>
        </S.InputColumn>
        <S.InputColumn>
          <S.Label>거래 시간</S.Label>
          <S.HelpText><FaRegQuestionCircle /><span>거래가 발생한 시간에 대해서 적어주세요.</span></S.HelpText>
          <S.DatetimeWrapper>
            <S.DateTime>
              <Select
                title="년도"
                name="yearSelect"
                label="서버를 선택하세요"
                value={yearSelect}
                handleOnChange={handleOnChange}
                menuItems={year} />
            </S.DateTime>
            <S.DateTime>
              <Select
                title="월"
                name="monthSelect"
                label="서버를 선택하세요"
                value={monthSelect}
                handleOnChange={handleOnChange}
                menuItems={month} />
            </S.DateTime>
            <S.DateTime>
              <Select
                title="일"
                name="daySelect"
                label="서버를 선택하세요"
                handleOnChange={handleOnChange}
                value={daySelect}
                menuItems={day} />
            </S.DateTime>
          </S.DatetimeWrapper>
        </S.InputColumn>
        <S.InputColumn>
          <S.Label>문제 상황</S.Label>
          <S.HelpText><FaRegQuestionCircle /><span>문제의 상황에 대해서 10자 ~ 300자 이내로 적어주세요</span></S.HelpText>
          <S.SituationWrapper>
            <InputBox
              styleType="Multiline"
              name="situation"
              handleOnChange={handleOnChange}
              value={situation}
              label="문제의 상황을 입력해주세요" />
          </S.SituationWrapper>
        </S.InputColumn>
        <S.InputColumn>
          <S.Label>증거 이미지 제출</S.Label>
          <S.HelpText><FaRegQuestionCircle /><span>신고서의 진위여부를 더욱 잘 판단할 수 있도록 거래시 나눴던 대화의 캡쳐나 거래 내역을 5개 이내로 업로드해주세요</span></S.HelpText>
          <Alert style={{ marginBottom: "20px" }} severity="info">증거 이미지는 필수로 포함되어야 하며 10MB 제한의 최대 5개의 이미지 파일 (png, jpg, jpeg) 만 허용합니다.</Alert>
          <FileDropzone handleOnFileUpload={handleOnFileUpload} filesLimit={5} maxFileSize={10000000} />
        </S.InputColumn>
        <S.SubmitButtonWrapper>
          <div style={{ marginBottom: "30px" }}>
            {buttonLock ? (
              <Alert severity="warning">모든 입력 필드를 채워야 제출이 가능합니다.</Alert>
            ) : (
                <Alert severity="success">제출 버튼을 클릭해주세요!</Alert>
              )}
          </div>
          <Button loading={loading} handleOnClick={handleSubmitButtonClick} disabled={buttonLock} width={80} height={45} bold={true} label="제출" />
        </S.SubmitButtonWrapper>
      </S.ReportWrapper>
    </S.Container>
  )
}

export default ReportTemplate
