import React, { useState } from 'react'

import * as S from "./styles";

const EvidenceImage = ({ imageUrl }) => {

  return (
    <S.Container>
      <S.Image src={imageUrl && imageUrl} />
    </S.Container>
  )
}

export default EvidenceImage
