import { ThemeProvider } from "styled-components";
import { ThemeProvider as MuiThemeProvider } from "@mui/material/styles";
import GlobalStyles from "../../styles/global-styles";
import theme from "../../styles/theme";
import Navigation from "../component/module/navigation";
import Footer from "../component/module/footer";
import muiTheme from "../../styles/muiTheme";

const MyApp = ({ Component, pageProps }) => {
  return (
    <>
      <GlobalStyles />
      <MuiThemeProvider theme={muiTheme}>
        <ThemeProvider theme={theme}>
          <Navigation />
          <Component {...pageProps} />
          <Footer />
        </ThemeProvider>
      </MuiThemeProvider>
    </>
  );
}

export default MyApp
