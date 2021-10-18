import React from 'react'

import HeroCard from "../../card/hero-card";

const HeroGroup = () => {
  return (
    <div>
      <HeroCard title="피해 신고" description="사기를 당했다면 상대방의 캐릭터를 다른사람들이 조심할 수 있도록 신고해주세요!" src="trade4.svg" />
      <HeroCard title="사기꾼 검색" description="사기꾼을 검색하고 피해를 미리 방지하세요!" src="trade3.svg" isLeft />
    </div>
  )
}

export default HeroGroup
