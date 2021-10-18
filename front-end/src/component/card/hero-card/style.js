import styled from 'styled-components';

export const Container = styled.div`
  width: 100%;
  height: 800px;

  display: flex;
  justify-content: center;
  align-items: center;

  flex-direction: ${({ isLeft }) => isLeft ? "row" : "row-reverse"};
  background: ${({ isLeft }) => isLeft ? "" : "white"};

  @media screen and (max-width: 768px) {
    flex-direction: column-reverse;
  }
`;

export const TextWrapper = styled.div`
  margin: 0 80px;
  @media screen and (max-width: 768px) {
    margin: 80px 0;
  }
`;

export const Title = styled.h1`
  font-size: 2.4rem;
  font-weight: bold;
  width: 300px;
  margin-bottom: 20px;
  text-align: center;
  line-height: 1.5;

  @media screen and (max-width: 768px) {
    font-size: 2rem;
  }
`;

export const Description = styled.p`
  color: ${props => props.theme.color.gray};
  width: 300px;
  line-height: 1.8;
`;

export const ImageWrapper = styled.div`
  margin: 0 80px;
  @media screen and (max-width: 768px) {
    margin: 80px 0;
  }
`;

export const Image = styled.img`
  width: 300px;

  @media screen and (max-width: 768px) {
    width: 200px;
  }
`;