import React from 'react'

import InputBox from "../../module/input-box";
import * as S from './styles';

const index = () => {
  return (
    <S.Container>
      <div>
        <S.InputWrapper>
          <InputBox styleType="Simple" label="아이디를 입력하세요" />
        </S.InputWrapper>
      </div>

      <div>
        <S.InputWrapper>
          <InputBox styleType="Password" label="비밀번호를 입력하세요" />
        </S.InputWrapper>
      </div>

      <div>
        <S.InputWrapper>
          <InputBox styleType="Multiline" label="피해 상황을 상세하게 입력해주세요" />
        </S.InputWrapper>
      </div>

      <div>
        <S.InputWrapper>
          <InputBox styleType="Simple" />
        </S.InputWrapper>
      </div>

      <div>
        <S.InputWrapper>
          <InputBox styleType="Simple" />
        </S.InputWrapper>
      </div>
    </S.Container>
  )
}

export default index;
