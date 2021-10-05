import styled from 'styled-components';

export const Container = styled.section`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
`;

export const ReportWrapper = styled.div`
  width: 85%;
  max-width: 1100px;
  background: white;
  display: flex;
  justify-content: center;
  align-items: flex-start;
  flex-direction: column;
  background: ${props => props.theme.color.white};
  padding: 100px;
  border-radius: 15px;
  box-shadow: 0px 3px 22px rgba(0, 0, 0, 0.16);
  margin-bottom: 100px;
  @media screen and (max-width: 576px) {
    padding: 20px;
  }
`;

export const Label = styled.div`
  color: ${props => props.theme.color.defaultFont};
  margin-bottom: 16px;
  font-size: 1.1rem;
  font-weight: bold;

  @media screen and (max-width: 576px) {
    font-size: 0.8rem;
  }
`;

export const ServerSelectWrapper = styled.div`
  width: 30%;
`;

export const SituationWrapper = styled.div`
  width: 100%;
`;

export const NicknameWrapper = styled.div`
  width: 50%;
  @media screen and (max-width: 576px) {
    width: 80%;
  }
`;

export const DatetimeWrapper = styled.div`
  display: flex;
  width: 20%;
  @media screen and (max-width: 576px) {
    flex-wrap: wrap;
  }
`;

export const DateTime = styled.div`
  margin: 10px 10px 10px 0;
`;

export const InputColumn = styled.div`
  margin: 20px 0;
  display: flex;
  width: 100%;
  justify-content: center;
  align-items: flex-start;
  flex-direction: column;
`;

export const SubmitButtonWrapper = styled.div`
  width: 100%;
  display: flex;
  margin-top: 60px;
  justify-content: center;
  align-items: center;
  flex-direction: column;
`;