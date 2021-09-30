import styled from 'styled-components';

export const ResetCssRevert = styled.div`
  * {
    all: revert;
  }

  display: flex;
  justify-content: center;
  align-items: center;
`;

export const Container = styled.div`
  background: ${props => props.theme.color.white};
  width: 80%;
  max-width: 1300px;
  font-size: 1rem;
  line-height: 2.5rem;
  color: ${props => props.theme.color.deepBlue};
  box-shadow: 0px 3px 22px rgba(0, 0, 0, 0.16);
  margin-bottom: 140px;
  padding: 50px;
  border-radius: 20px;
`;

export const InlineCode = styled.span`
  background: yellow;
`;

export const CodeBlockWrapper = styled.pre`
  background: #F3F3F3;
  padding: 2rem;
  line-height: 1.5rem;
  margin: 2rem auto;
`;

export const Link = styled.a`
  color: #7BD7BD;
  font-weight: bold;
  text-decoration: none;
`;

export const H1Wrapper = styled.div`
  font-size: 1.2rem;
`;