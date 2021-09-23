import { ThemeProvider } from "styled-components";

import GlobalStyles from "../../styles/global-styles";
import theme from "../../styles/theme";
import Navigation from "../component/navigation";

const MyApp = ({ Component, pageProps }) => {
  return (
    <>
      <GlobalStyles />
      <ThemeProvider theme={theme}>
        <Navigation />
        <Component {...pageProps} />
      </ThemeProvider>
    </>
  );
}

export default MyApp
