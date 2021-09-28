import { useEffect } from "react";
import { Provider } from "react-redux";
import { ThemeProvider } from "styled-components";

import { ThemeProvider as MuiThemeProvider } from "@mui/material/styles";

import theme from "../../styles/theme";
import muiTheme from "../../styles/muiTheme";

import GlobalStyles from "../../styles/global-styles";

import Navigation from "../component/module/navigation";
import Footer from "../component/module/footer";

import store from "../reducers";
import * as Storage from "../utils/storage";

const MyApp = ({ Component, pageProps }) => {

  useEffect(() => {
    const accessToken = Storage.getAccessToken();

    if (accessToken === null) {
      // validateUser 하지 않고 return
    }

    // validateUser(accessToken);
    // 응답으로 userId, email, nickname 을 받음
    // 만약 token 이 invalid 한 경우에 401 unauthorized error 를 발생
    // 401 error 라면 do noting
    // 그 외의 경우 storage 에 setting
  }, []);

  return (
    <>
      <GlobalStyles />
      <MuiThemeProvider theme={muiTheme}>
        <ThemeProvider theme={theme}>
          <Provider store={store}>
            <Navigation />
            <Component {...pageProps} />
            <Footer />
          </Provider>
        </ThemeProvider>
      </MuiThemeProvider>
    </>
  );
}

export default MyApp
