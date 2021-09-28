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

export const AvatarWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  margin: 15px 0;
`;

export const Nickname = styled.span`
  font-size: 1rem;
  margin: 6px 0;
`;

export const Email = styled.span`
  font-size: 0.9rem;
  word-break: break-word;
  margin-top: 6px;
  text-align: center;
`;