import React from 'react'

import * as S from "./styles";

import PageHeader from "../../module/page-header";
import Select from '../../module/select';
import InputBox from "../../module/input-box";
import FileDropzone from "../../module/file-dropzone";
import Modal from "../../modal-template/modal";
import ReportSubmit from "../../modal-template/report-submit";

import { server, cheatingType, year, month, day } from "../../../constants/select-value";


const ReportTemplate = ({ values, handleOnChange, handleOnFileUpload }) => {

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
          <S.NicknameWrapper>
            <InputBox
              styleType="Simple"
              name="nickname"
              value={nickname}
              handleOnChange={handleOnChange}
              label="거래를 했던 상대 캐릭터 닉네임을 입력하세요" />
          </S.NicknameWrapper>
        </S.InputColumn>
        <S.InputColumn>
          <S.Label>신고 종류</S.Label>
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
          <S.SituationWrapper>
            <InputBox
              styleType="Multiline"
              name="situation"
              handleOnChange={handleOnChange}
              value={situation}
              label="문제의 상황을 500자 이내로 입력해주세요" />
          </S.SituationWrapper>
        </S.InputColumn>
        <S.InputColumn>
          <S.Label>증거 이미지 제출</S.Label>
          <FileDropzone handleOnFileUpload={handleOnFileUpload} filesLimit={5} />
        </S.InputColumn>
      </S.ReportWrapper>
    </S.Container>
  )
}

export default ReportTemplate
