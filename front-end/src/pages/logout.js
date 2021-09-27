import React, { useEffect } from 'react'
import { useRouter } from "next/router";

import Loading from "../component/module/loading";

import * as Storage from "../utils/storage";

const logout = () => {

  const router = useRouter();

  useEffect(() => {
    const user = Storage.getUser();

    if (user === null) {
      router.replace("/");
    }

    Storage.clearUser();
    router.replace("/");
  }, []);

  return (
    <div style={{ height: "100vh" }}>
      <Loading />
    </div>
  )
}

export default logout
