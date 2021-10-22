import { useState } from 'react'
import { useDispatch, useSelector } from "react-redux"

import { searchValidator, emptyTextValidator } from "../utils/validator";
import { getCheater } from "../reducers/cheater";
import { enableAlert } from "../reducers/application";
import * as Storage from "../utils/storage";

import Search from "../component/section/search";

const Cheater = () => {
  const dispatch = useDispatch();

  const { nickname } = useSelector(state => state.cheaterReducer.search);

  const [searchText, setSearchText] = useState(nickname);

  const handleOnChange = (e) => {
    const { value } = e.target;
    setSearchText(value);
  }

  const handleSearchOnClick = () => {
    if (!emptyTextValidator(searchText)) {
      const content = {
        title: "입력 값 에러",
        message: "거래를 진행하려는 캐릭터의 닉네임을 입력해주세요!",
        severity: "error"
      }
      dispatch(enableAlert(content));
      return;
    }

    if (!searchValidator(searchText)) {
      const content = {
        title: "입력 값 에러",
        message: "캐릭터 검색은 2-12글자 사이 공백을 제외한 한글, 영문, 숫자만 허용합니다.",
        severity: "error"
      }
      dispatch(enableAlert(content));
      return;
    }

    dispatch(getCheater(searchText));
  }

  return <Search
    handleOnChange={handleOnChange}
    handleSearchOnClick={handleSearchOnClick}
    searchText={searchText} />
}

export default Cheater;
