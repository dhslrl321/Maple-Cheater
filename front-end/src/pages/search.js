import { useState } from 'react'
import { useDispatch, useSelector } from "react-redux"
import withAuthentication from "../higher-order-component/with-authentication";

import { whitespaceValidator } from "../utils/validator";
import { getCheater } from "../reducers/cheater";
import { showAlert } from "../reducers/application";
import * as Storage from "../utils/storage";

import Search from "../component/section/search";

const cheater = () => {
  const dispatch = useDispatch();

  const { nickname } = useSelector(state => state.cheaterReducer.search);

  const [searchText, setSearchText] = useState(nickname);

  const handleOnChange = (e) => {
    const { value } = e.target;
    setSearchText(value);
  }

  const handleSearchOnClick = () => {
    if (searchText === "") {
      const content = {
        title: "입력 값 에러",
        message: "거래를 진행하려는 캐릭터의 닉네임을 입력해주세요!",
        severity: "error"
      }
      dispatch(showAlert(content));
      return;
    }

    if (whitespaceValidator(searchText)) {
      const content = {
        title: "입력 값 에러",
        message: "닉네임에는 공백을 허용하지 않습니다!",
        severity: "error"
      }
      dispatch(showAlert(content));
      return;
    }
    const accessToken = Storage.getAccessToken();
    dispatch(getCheater(accessToken, searchText));
  }

  return <Search
    handleOnChange={handleOnChange}
    handleSearchOnClick={handleSearchOnClick}
    searchText={searchText} />
}

export default withAuthentication(cheater);
