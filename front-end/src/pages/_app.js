import { Provider } from "react-redux";
import { ThemeProvider } from "styled-components";

import { ThemeProvider as MuiThemeProvider } from "@mui/material/styles";

import theme from "../../styles/theme";
import muiTheme from "../../styles/muiTheme";

import GlobalStyles from "../../styles/global-styles";

import Navigation from "../component/module/navigation";
import Footer from "../component/module/footer";

import store from "../reducers";

const MyApp = ({ Component, pageProps }) => {
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
