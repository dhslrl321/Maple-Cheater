import React from 'react'

import * as S from "./styles";

import PageHeader from "../../module/page-header";
import SearchBar from "../../module/search-bar";
import CharacterGroup from "../../group/charater-group";
import Hero from "../../card/hero";
import HeroGroup from "../../group/hero-group";

const index = ({ handleOnChange, handleSearchOnClick, searchText, loading }) => {
  return (
    <S.Container>
      <PageHeader
        title="메이플스토리 사기꾼 검색"
        subtitle="당신의 검색과 제보가 함께 메이플을 즐기는 용사에게는 큰 힘이 됩니다." />
      <SearchBar
        handleOnChange={handleOnChange}
        handleSearchOnClick={handleSearchOnClick}
        searchText={searchText}
        loading={loading} />
      <Hero />

      <CharacterGroup />
      <HeroGroup />
    </S.Container >
  )
}

export default index
