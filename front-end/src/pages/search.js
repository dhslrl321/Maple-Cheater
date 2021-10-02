import React from 'react'

import withAuthentication from "../higher-order-component/with-authentication";
import Search from "../component/section/search";

const cheater = () => {
  return <Search />
}

export default withAuthentication(cheater);
