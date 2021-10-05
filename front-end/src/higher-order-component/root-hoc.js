import { useEffect } from 'react'
import { useDispatch, useSelector } from "react-redux";
import Router from "next/router";

import ProgressBar from "@badrap/bar-of-progress";

import Navigation from "../component/module/navigation";
import Footer from "../component/module/footer";
import Alert from "../component/module/alert";

import { validateUser } from "../reducers/user";
import { disableAlert } from "../reducers/application";
import * as Storage from "../utils/storage";
import { tokenValidator } from "../utils/validator";

const progress = new ProgressBar({
  size: 2,
  color: "#5F56EF",
  className: "bar-of-progress",
  delay: 100,
});

Router.events.on("routeChangeStart", progress.start);
Router.events.on("routeChangeComplete", progress.finish);
Router.events.on("routeChangeError", progress.finish);

const rootHoc = ({ children }) => {

  const dispatch = useDispatch();

  const { data, status } = useSelector(state => state.userReducer.user);
  const { show, severity, title, message } = useSelector(state => state.applicationReducer.alert);

  useEffect(() => {
    const accessToken = Storage.getAccessToken();

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
    status === 403 && Storage.clearAll();
  }, []);

  if (status === 200) {
    const { userId, nickname, email } = data;
    const responseUser = {
      userId,
      nickname,
      email
    }
    Storage.setUser(responseUser);
  }

  const handleAlertClose = () => {
    dispatch(disableAlert());
  }

  return (
    <>
      <Navigation />
      <div style={{ minHeight: "1080px" }}>
        {children}
      </div>
      <Alert
        isOpen={show}
        handleAlertClose={handleAlertClose}
        severity={severity}
        title={title}
        message={message} />
      <Footer />
    </>
  )
}

export default rootHoc
