import { useEffect } from 'react'
import { useDispatch, useSelector } from "react-redux";

import Navigation from "../component/module/navigation";
import Footer from "../component/module/footer";

import { validateUser } from "../reducers/user";
import * as Storage from "../utils/storage";
import { tokenValidator } from "../utils/validator";

const rootHoc = ({ children }) => {

  const dispatch = useDispatch();

  const { status } = useSelector(state => state.userReducer.user);

  useEffect(() => {
    const accessToken = Storage.getAccessToken();
    const user = Storage.getUser();

    if (tokenValidator(accessToken)) {
      Storage.clearAll();
      return;
    }

    if (accessToken === null) {
      Storage.clearAll();
      return;
    }

    dispatch(validateUser(accessToken));
    status === 401 && Storage.clearAll();
  }, []);

  return (
    <>
      <Navigation />
      <div style={{ minHeight: "1080px" }}>
        {children}
      </div>
      <Footer />
    </>
  )
}

export default rootHoc
