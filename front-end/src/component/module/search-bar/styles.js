import styled from 'styled-components';

export const Container = styled.div`
  height: 100px;
  width: 100%;

  display: flex;
  justify-content: center;
  align-items: center;
  color: red;

`;

export const SearchBarWrapper = styled.div`
  width: 80%;
  display: flex;
  max-width: 1100px;

  @media screen and (max-width: 576px) {
    font-size: 0.7rem;
  }
`;

export const Button = styled.button`
  height: 55px;
  width: 100px;
  background: ${props => props.theme.color.primary};
  margin: 0 0 0 20px;
  color: ${props => props.theme.color.white};
  font-weight: bold;
  font-size: 1rem;
  border-radius: 6px;
  
  :hover {
    background: ${props => props.theme.color.hoverPrimary};
  }

  :active {
    background: ${props => props.theme.color.activePrimary};
    color: ${props => props.theme.color.white};
  }

  cursor: pointer;

  @media screen and (max-width: 768px) {
    height: 43px;
  }

  @media screen and (max-width: 576px) {
    font-size: 0.8rem;
  }
`;