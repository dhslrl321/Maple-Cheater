import styled from 'styled-components';

export const Button = styled.button`
  width: ${props => props.width + "px"};
  height: ${props => props.height + "px"};
  background: ${props => props.theme.color.primary};
  margin: ${({ withoutMargin }) => withoutMargin ? "" : "0 0 0 20px"};
  color: ${props => props.theme.color.white};
  
  font-size: 1rem;
  border-radius: 6px;

  font-weight: ${({ bold }) => bold ? "bold" : ""};

  opacity: ${({ disabled }) => disabled ? 0.65 : 1};
  cursor: ${({ disabled }) => disabled ? "not-allowed" : "pointer"};
  pointer-events: ${({ disabled }) => disabled ? "none" : "auto"};

  :hover {
    background: ${props => props.theme.color.hoverPrimary};
  }

  :active {
    background: ${props => props.theme.color.activePrimary};
    color: ${props => props.theme.color.white};
  }

  @media screen and (max-width: 576px) {
    font-size: 0.8rem;
  }
`;