import React from 'react'

import * as S from "./styles";
import SearchBar from "../../module/search-bar";
import PageHeader from "../../module/page-header";
import CheaterInfo from "../../module/cheater-info";

const Search = ({ handleOnChange, handleSearchOnClick, searchText, loading, data, status }) => {
  return (
    <S.Container>
      <PageHeader title="사용자 검색하기" subtitle="캐릭터 닉네임으로 해당 캐릭터의 신고 이력을 확인할 수 있습니다." />
      <SearchBar
        handleOnChange={handleOnChange}
        handleSearchOnClick={handleSearchOnClick}
        searchText={searchText}
        loading={loading} />
      <CheaterInfo data={data} status={status} />
    </S.Container>
  )
}

export default Search
