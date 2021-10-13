import styled from 'styled-components';

export const Container = styled.section`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
`;

export const WhiteBackground = styled.div`
  width: 85%;
  max-width: 1100px;
  background: white;
  display: flex;
  justify-content: center;
  align-items: flex-start;
  flex-direction: column;
  background: ${props => props.theme.color.white};
  padding: 50px;
  border-radius: 15px;
  box-shadow: 0px 3px 22px rgba(0, 0, 0, 0.16);
  margin-bottom: 100px;
  @media screen and (max-width: 576px) {
    padding: 20px;
  }
`;

export const Title = styled.h1`
  font-size: 1.3rem;
  font-weight: bold;
`;

export const Label = styled.div`
  margin: 8px 0;
  font-size: 1rem;
`;

export const InputWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: flex-start;
  flex-direction: column;
  margin: 14px 0;
`;

export const ButtonWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
`;