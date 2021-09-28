import React, { useEffect } from 'react'
import { useDispatch } from "react-redux";
import { useRouter } from "next/router";

import Loading from "../component/module/loading";

import * as Storage from "../utils/storage";
import { clearUser } from "../reducers/user";

const logout = () => {

  const router = useRouter();
  const dispatch = useDispatch();

  const clearAllUserData = () => {
    Storage.clearUser();
    dispatch(clearUser());
  }

  useEffect(() => {
    const user = Storage.getUser();

    if (user === null) {
      router.replace("/");
    }

    clearAllUserData();

    router.replace("/");
  }, []);

  return (
    <div style={{ height: "100vh" }}>
      <Loading />
    </div>
  )
}

export default logout
