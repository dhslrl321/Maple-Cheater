import React from 'react'

import withAuthentication from "../higher-order-component/with-authentication";

const cheater = () => {
  return (
    <div>
      사용자 검색
    </div>
  )
}

export default withAuthentication(cheater);
