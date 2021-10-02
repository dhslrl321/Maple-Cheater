import React from 'react'

import * as S from "./styles";
import SearchBar from "../../module/search-bar";
import PageHeader from "../../module/page-header";
import CheaterInfo from "../../module/cheater-info";

const Search = () => {
  return (
    <S.Container>
      <PageHeader title="사용자 검색하기" subtitle="캐릭터 닉네임으로 해당 캐릭터의 신고 이력을 확인할 수 있습니다." />
      <SearchBar />
      <CheaterInfo />
    </S.Container>
  )
}

export default Search
