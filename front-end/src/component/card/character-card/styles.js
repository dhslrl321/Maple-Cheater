import styled from 'styled-components';

export const Container = styled.div`
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;

  margin: 25px 0;
`;

export const CardWrapper = styled.div`
  width: 70%;
  height: 150px;

  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: ${({ isLeft }) => (isLeft ? "row" : "row-reverse")};
  background: #F0F0F1;
  color: ${props => props.theme.color.defaultFont};

  border-radius: 14px;
  box-shadow: 0px 3px 22px rgba(0, 0, 0, 0.16);

  max-width: 1100px;

  @media screen and (max-width: 900px) {
    width: 80%;
    height: 180px;
  }

  @media screen and (max-width: 576px) {
    height: 230px;
  }
`;

export const CardImageWrapper = styled.div`
  display: flex;
  width: 30%;
  justify-content: center;
  align-items: center;
  flex-direction: column;
`;

export const CardImage = styled.img`
  width: 170px;
  margin-bottom: 30px;
  transform: scaleX(-1);
  transform: ${({ isLeft }) => (isLeft ? "scaleX(-1)" : "scaleX(1)")};
`;

export const CardTextWrapper = styled.div`
  display: flex;
  width: 70%;
  justify-content: center;
  align-items: flex-start;
  flex-direction: column;
  line-height: 1.7;
  margin-left: ${({ isLeft }) => (isLeft ? "0" : "10%")};
`;

export const CardTitle = styled.h2`
  font-size: 1.2rem;
  font-weight: bold;
  margin: 10px 20px;

  @media screen and (max-width: 900px) {
    font-size: 0.9rem;
  }

  @media screen and (max-width: 576) {
    font-size: 0.8rem;
  }
`;

export const CardParagraph = styled.p`
  width: 70%;
  margin: 10px 20px;
  font-size: 0.9rem;

  @media screen and (max-width: 900px) {
    font-size: 0.8rem;
  }

  @media screen and (max-width: 576px) {
    font-size: 0.7rem;
  }
`;

