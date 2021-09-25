import styled from 'styled-components';

export const LinkItem = styled.div`
  width: 100px;
`;

export const LinkColumn = styled.div`
  height: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
`;

export const AuthLinkColumn = styled.div`
  font-weight: bold;
  height: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
`;

export const DrawerTitle = styled.h1`
  width: 100%;
  height: 50px;
  font-size: 1.2rem;
  display: flex;
  justify-content: center;
  align-items: center;
  color: ${props => props.theme.color.primary};
  font-weight: bold;
`;