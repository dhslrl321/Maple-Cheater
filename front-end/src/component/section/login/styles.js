import styled from 'styled-components';

export const Container = styled.div`
  width: 100%;
  height: 80vh;
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

export const Label = styled.div`
  color: ${props => props.theme.color.defaultFont};
  margin-bottom: 16px;
`;

export const FindPasswordWrapper = styled.div`
  width: 100%;
  display: flex;
  justify-content: flex-end;
  align-items: center;

  margin-top: 12px;
`;

export const FindPasswordLabel = styled.span`
  color: ${props => props.theme.color.primary};
  cursor: pointer;
  user-select: none;
  -webkit-touch-callout: none;
  -webkit-user-select: none;
  -khtml-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  
  :active {
    color: ${props => props.theme.color.activePrimary};
  }
`;

export const ButtonWrapper = styled.div`
  margin: 40px 0;
`;
