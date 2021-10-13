import { useState } from "react";
import { useRouter } from "next/router";
import { useDispatch } from "react-redux";

import { searchValidator, emptyTextValidator } from "../utils/validator";
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
    if (!searchValidator(searchText)) {
      const content = {
        title: "입력 값 에러",
        message: "캐릭터 검색은 2-12글자 사이 공백을 제외한 한글, 영문, 숫자만 허용합니다.",
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