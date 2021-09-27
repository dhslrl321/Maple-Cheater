import React from 'react'

import AlertSelfClose from "../component/module/alert-self-close";
import Loading from "../component/module/loading";

import useAuthentication from "../hooks/use-authentication";

const withAuthentication = WrappedComponent => {
  const container = () => {
    const user = useAuthentication();

    return !user ? (
      <>
        <Loading />
        <AlertSelfClose
          severity="warning"
          title="제목"
          message="메시지"
        />
      </>
    ) : (
        <WrappedComponent />
      );
  }

  return container;
}

export default withAuthentication;
