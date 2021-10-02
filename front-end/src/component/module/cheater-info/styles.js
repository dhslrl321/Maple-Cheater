import styled from 'styled-components';

export const Container = styled.div`
  width: 80%;
  max-width: 1100px;
  display: flex;
  justify-content: center;
  align-items: flex-start;
  flex-direction: column;
`;

export const Title = styled.h1`
  font-size: 1.4rem;
  margin: 18px 0;
  font-weight: bold;

  @media screen and (max-width: 576px) {
    font-size: 1.2rem;
  }
`;

export const CheaterCard = styled.div`
  width: 100%;
  max-width: 1100px;
  height: 120px;
  background: ${props => props.theme.color.white};
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-radius: 15px;
  box-shadow: 0px 3px 22px rgba(0, 0, 0, 0.16);
  font-size: 0.9rem;
  padding: 0 20px;
  margin-bottom:20px;
`;

export const Manual = styled.div`
  line-height: 2;
`;

export const CheaterWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  
  width: 100%;
`;

export const NicknameWrapper = styled.h3`
  margin-right: 10px;
`;

export const CheaterDetailWrapper = styled.div`
  width: 100%;
  max-width: 1100px;
  background: ${props => props.theme.color.white};
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-radius: 15px;
  box-shadow: 0px 3px 22px rgba(0, 0, 0, 0.16);
  font-size: 0.9rem;
  padding: 10px 20px;
`;