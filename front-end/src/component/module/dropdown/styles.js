import styled from 'styled-components';

export const Container = styled.nav`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  width: 130px;
  background: white;
  position: absolute;
  top: 50px;
  box-shadow: 0px 3px 22px rgba(0, 0, 0, 0.16);
  border-radius: 10px;
  display: ${({ dropdown }) => dropdown ? "flex" : "none"};
  font-size: 0.8rem;
  color: ${props => props.theme.color.gray};
`;

export const LinkList = styled.div`
  margin: 10px;
  width: 100%;
  padding-left: 15px;
  :hover {
    color: ${props => props.theme.color.primary}
  }
`;

export const UserProfile = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  text-align: center;
  width: 100%;
`;

export const AvatarWrapper = styled.div`
  margin: 16px 0 5px 0;
`;

export const Nickname = styled.span`
  font-size: 1rem;
  margin: 6px 0;
`;

export const Email = styled.span`
  font-size: 0.6rem;
  width: 120px;
  word-break: break-word;
  margin: 6px 0 10px 0;
  text-align: center;
`;