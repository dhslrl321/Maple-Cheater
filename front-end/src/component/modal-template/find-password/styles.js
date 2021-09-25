import styled from 'styled-components';

export const InputWrapper = styled.div`
  margin: 10px 0;

  @media screen and (max-width: 576px) {
    width: 340px;
  }

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
`;

export const Label = styled.div`
  color: ${props => props.theme.color.defaultFont};
  margin: 30px 0;
`;

export const ButtonWrapper = styled.div`
  margin: 40px 0;
`;

export const SendButtonWrapper = styled.div`
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;

  margin-top: 35px;
`;