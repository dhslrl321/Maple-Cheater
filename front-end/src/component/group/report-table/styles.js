import styled from 'styled-components';

export const Container = styled.div`
  font-size: 2rem;
`;

export const EmptyDetailText = styled.span`
  margin: 10px 0;
`;

export const Title = styled.span`
  font-size: 1rem;
  font-weight: bold;

  @media screen and (max-width: 576px) {
    font-size: 0.8rem;
  }
`;

export const Text = styled.span`
  font-size: 0.8rem;
  @media screen and (max-width: 576px) {
    font-size: 0.7rem;
  }
`;