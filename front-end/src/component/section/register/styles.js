import styled from 'styled-components';


export const Container = styled.div`
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-bottom: 50px;
`;

export const InputWrapper = styled.div`
  width: 400px;
  margin: 10px 0;

  @media screen and (max-width: 576px) {
    width: 340px;
  }
`;

export const Column = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;

export const Label = styled.div`
  color: ${props => props.theme.color.defaultFont};
  margin-bottom: 16px;
`;

export const ButtonWrapper = styled.div`
  margin: 40px 0;
`;