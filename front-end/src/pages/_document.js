import React from 'react';
import Document, { Html, Head, Main, NextScript } from 'next/document';
import { ServerStyleSheet } from 'styled-components'
import { ServerStyleSheets } from '@material-ui/styles';

class MyDocument extends Document {
  static async getInitialProps(ctx) {
    const styledComponentsSheet = new ServerStyleSheet()
    const materialSheets = new ServerStyleSheets()
    const originalRenderPage = ctx.renderPage;

    try {
      ctx.renderPage = () => originalRenderPage({
        enhanceApp: App =>
          props => styledComponentsSheet.collectStyles(materialSheets.collect(<App {...props} />))
      })
      const initialProps = await Document.getInitialProps(ctx)
      return {
        ...initialProps,
        styles: (
          <React.Fragment>
            {initialProps.styles}
            {materialSheets.getStyleElement()}
            {styledComponentsSheet.getStyleElement()}
          </React.Fragment>
        )
      }
    } finally {
      styledComponentsSheet.seal()
    }
  }

  render() {
    return (
      <Html lang="en" dir="ltr">
        <Head>
          <meta charSet="utf-8" />
        </Head>
        <body>
          <Main />
          <NextScript />
        </body>
      </Html>
    );
  }
}

MyDocument.displayName = "MyDocument";

export default MyDocument;