import styled from 'styled-components';

export const Container = styled.div`
  width: 100%;
  height: 100px;
  background: ${props => props.theme.color.dark};
`;

export const TextWrapper = styled.div`
  display: flex;
  height: 100%;
  justify-content: center;
  align-items: flex-start;
  flex-direction: column;
`;

export const Text = styled.span`
  font-size: 0.8rem;
  color: ${props => props.theme.color.white};
  margin: 6px 40px;

  @media screen and (max-width: 768px) {
    font-size: 0.6rem;
  }
`;