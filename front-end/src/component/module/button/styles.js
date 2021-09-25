import styled from 'styled-components';

export const Button = styled.button`
  width: ${props => props.width + "px"};
  height: ${props => props.height + "px"};
  background: ${props => props.theme.color.primary};
  margin: 0 0 0 20px;
  color: ${props => props.theme.color.white};
  
  font-size: 1rem;
  border-radius: 6px;

  font-weight: ${({ bold }) => bold ? "bold" : ""};

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