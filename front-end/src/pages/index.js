import { useState } from "react";
import { useRouter } from "next/router";
import { useDispatch } from "react-redux";

import { whitespaceValidator } from "../utils/validator";
import * as Storage from "../utils/storage";
import { setSearchNickname, getCheater } from "../reducers/cheater";
import { enableAlert } from "../reducers/application";

import Home from "../component/section/home";

const home = () => {
  const router = useRouter();
  const dispatch = useDispatch();

  const [searchText, setSearchText] = useState("");
  const [loading, setLoading] = useState(false);


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
      dispatch(enableAlert(content));
      return;
    }

    if (whitespaceValidator(searchText)) {
      const content = {
        title: "입력 값 에러",
        message: "닉네임에는 공백을 허용하지 않습니다",
        severity: "error"
      }
      dispatch(enableAlert(content));
      return;
    }
    setLoading(true);
    const accessToken = Storage.getAccessToken();
    dispatch(setSearchNickname(searchText));
    dispatch(getCheater(accessToken, searchText));
    router.push("/search");
  }

  return <Home
    handleOnChange={handleOnChange}
    handleSearchOnClick={handleSearchOnClick}
    searchText={searchText}
    loading={loading} />

};
export default home;