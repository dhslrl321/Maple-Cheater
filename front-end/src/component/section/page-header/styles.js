import styled from 'styled-components';

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

export const TitleWrapper = styled.div`
  margin: 24px 0;
`;

export const Title = styled.h1`
  font-size: 1.8rem;
  font-weight: bold;
  color: ${props => props.theme.color.defaultFont};

  @media screen and (max-width: 576px) {
    font-size: 1.2rem;
  }
`;

export const Subtitle = styled.h3`
  font-size: 1.2rem;
  color: ${props => props.theme.color.gray};

  @media screen and (max-width: 576px) {
    font-size: 0.9rem;
    margin: 20px;
  }
`;