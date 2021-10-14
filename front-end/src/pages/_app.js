import Head from "next/head";
import { Provider } from "react-redux";
import { ThemeProvider } from "styled-components";

import { ThemeProvider as MuiThemeProvider } from "@mui/material/styles";

import theme from "../../styles/theme";
import muiTheme from "../../styles/muiTheme";

import GlobalStyles from "../../styles/global-styles";

import RootHoc from "../higher-order-component/root-hoc";

import store from "../reducers";

const MyApp = ({ Component, pageProps }) => {

  return (
    <>
      <GlobalStyles />
      <Head>
        <title>메이플스토리 사기꾼 검색 | maplecheater</title>
        <link rel="shortcut icon" href="/favicon.ico" />
        <link rel="icon" href="/favicon.ico" />
        <meta
          name="description"
          content="지금 MapleCheater 에서 메이플스토리 사기꾼을 검색해보세요!"
        />
        <meta property="og:title" content="메이플스토리 사기꾼 검색 | maplecheater" />
        <meta
          property="og:description"
          content="Maplestory 에서 발생하는 신뢰 기반 거래에 도움이 되고자 사기꾼을 등록하고 저장할 수 있는 서비스입니다."
        />
      </Head>
      <MuiThemeProvider theme={muiTheme}>
        <ThemeProvider theme={theme}>
          <Provider store={store}>
            <RootHoc>
              <Component {...pageProps} />
            </RootHoc>
          </Provider>
        </ThemeProvider>
      </MuiThemeProvider>
    </>
  );
}

export default MyApp
