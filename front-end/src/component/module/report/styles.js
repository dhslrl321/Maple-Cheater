import styled from 'styled-components';

export const Container = styled.div`
  width: 85%;
  background: ${props => props.theme.color.white};
  padding: 10px;
  border-radius: 15px;
  max-width: 1100px;
  box-shadow: 0px 3px 22px rgba(0, 0, 0, 0.16);

  @media screen and (max-width: 768px) {
    padding: 2px;
  }
`;

export const ReportDetailWrapper = styled.div`
  
`;

export const HeaderWrapper = styled.div`
  padding: 40px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  font-size: 1.1rem;
  font-weight: bold;
  margin: 18px;
  @media screen and (max-width: 576px) {
    padding: 10px;
    font-size: 0.8rem;
  }
`;

export const HeaderColumn = styled.div`
  display: flex;
  justify-content: flex-start;
  align-items: center;
  div {
    margin: 0 30px;
  }

  @media screen and (max-width: 768px) {
    div {
      margin: 2px;
    }
  }
`;

export const ContentWrapper = styled.div`
  padding: 40px;
  margin: 40px 0;
  @media screen and (max-width: 576px) {
    margin: 0;
    padding: 30px;
  }

`;

export const Title = styled.h3`
  width: 150px;
  font-weight: bold;
  @media screen and (max-width: 768px) {
    font-size: 0.9rem;
    width: 120px;
  }
`;

export const Content = styled.div`
  display: flex;
  margin: 0 0 80px 0;
  @media screen and (max-width: 576px) {
    margin: 0 0 40px 0;
  }
`;
export const ImageContent = styled.div`
  display: flex;
  flex-direction: column;
  margin: 0 0 80px 0;
`;

export const ImageWrapper = styled.div`
`;

export const Image = styled.img`
  width: 150px;
  height: 150px;
  border: 1px solid ${props => props.theme.color.gray};
  border-radius: 15px;
  object-fit: cover;
  cursor: pointer;
  margin: 15px;

  @media screen and (max-width: 576px) {
    width: 80px;
    height: 80px;
  }
`;

export const Text = styled.span`
  word-break: break-all;
  @media screen and (max-width: 768px) {
    font-size: 0.8rem;
  }
`;