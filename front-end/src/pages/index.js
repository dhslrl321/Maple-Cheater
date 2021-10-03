import { useState } from "react";
import { useRouter } from "next/router";
import { useDispatch } from "react-redux";

import { whitespaceValidator } from "../utils/validator";
import { setSearchNickname } from "../reducers/cheater";
import { showAlert } from "../reducers/application";

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
        severity: "info"
      }
      dispatch(showAlert(content));
      return;
    }

    if (whitespaceValidator(searchText)) {
      const content = {
        title: "입력 값 에러",
        message: "닉네임에는 공백을 포함시키지 않습니다ㅠㅠ",
        severity: "info"
      }
      dispatch(showAlert(content));
      return;
    }
    setLoading(true);
    dispatch(setSearchNickname(searchText));

    router.push("/search");
  }

  return <Home
    handleOnChange={handleOnChange}
    handleSearchOnClick={handleSearchOnClick}
    searchText={searchText}
    loading={loading} />

};
export default home;