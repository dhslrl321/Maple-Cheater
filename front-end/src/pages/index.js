import CharacterGroup from "../component/group/charater-group";
import PageHeader from "../component/section/page-header";
import SearchBar from "../component/section/search-bar";

import styled from 'styled-components';


const Home = () => {
  return (
    <>
      <PageHeader
        title="안전한 메이플월드를 함께 만들어주세요"
        subtitle="당신의 검색과 제보가 함께 메이플을 즐기는 용사에게는 큰 힘이 됩니다." />
      <SearchBar />
      <CharacterGroup />
    </>
  )
};

export default Home;