import { ThemeProvider } from "styled-components";

import GlobalStyles from "../../styles/global-styles";
import theme from "../../styles/theme";
import Navigation from "../component/section/navigation";
import Footer from "../component/section/footer";

const MyApp = ({ Component, pageProps }) => {
  return (
    <>
      <GlobalStyles />
      <ThemeProvider theme={theme}>
        <Navigation />
        <Component {...pageProps} />
        <Footer />
      </ThemeProvider>
    </>
  );
}

export default MyApp
