import React from 'react'

import * as S from "./styles";

import PageHeader from "../../module/page-header";
import SearchBar from "../../module/search-bar";
import CharacterGroup from "../../group/charater-group";
import Hero from "../../card/hero";

const index = ({ handleOnChange, handleSearchOnClick, searchText, loading }) => {
  return (
    <S.Container>
      <PageHeader
        title="안전한 메이플월드를 함께 만들어주세요"
        subtitle="당신의 검색과 제보가 함께 메이플을 즐기는 용사에게는 큰 힘이 됩니다." />
      <SearchBar
        handleOnChange={handleOnChange}
        handleSearchOnClick={handleSearchOnClick}
        searchText={searchText}
        loading={loading} />
      <Hero />
      <CharacterGroup />
    </S.Container >
  )
}

export default index
