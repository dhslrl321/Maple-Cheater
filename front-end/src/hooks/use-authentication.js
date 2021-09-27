import React, { useState, useEffect } from 'react'

import { useRouter } from "next/router";

import * as Storage from "../utils/storage";

const useAuthentication = () => {

  const router = useRouter();

  const [user, setUser] = useState(null);

  useEffect(() => {
    const userData = Storage.getUser();
    if (!userData) {
      router.push("/login");
      return;
    }

    setUser(userData);
  }, [])
  return user;
}

export default useAuthentication;
