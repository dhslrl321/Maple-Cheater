import React from 'react'

import ReactMarkdown from "react-markdown";

import * as S from "./styles";

const CodeBlock = ({ children }) => {
  return (
    <S.CodeBlockWrapper>
      <code>{children}</code>
    </S.CodeBlockWrapper>
  )
}

const H1 = ({ children }) => {
  return (
    <S.H1Wrapper>
      <h1>
        {children}
      </h1>
      <hr />
    </S.H1Wrapper>
  )
}

const Markdown = ({ textData }) => {

  return (
    <S.ResetCssRevert>
      <S.Container>
        <ReactMarkdown
          components={{
            h1: H1,
            a: S.Link,
            code: CodeBlock,
            inlineCode: S.InlineCode
          }}
          children={textData} />
      </S.Container>
    </S.ResetCssRevert>
  )
}

export default Markdown;
