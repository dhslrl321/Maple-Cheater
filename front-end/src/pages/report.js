import React from 'react'

import withAuthentication from "../higher-order-component/with-authentication";

const report = () => {
  return (
    <div>
      피해 등록
    </div>
  )
}

export default withAuthentication(report);
