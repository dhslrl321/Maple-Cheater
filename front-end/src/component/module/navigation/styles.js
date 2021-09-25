import styled from 'styled-components';

export const Back = styled.div`
  height: 60px;
  margin-bottom: 60px;
`;

export const Container = styled.nav`
  width: 100%;
  height: 60px;
  background: ${props => props.theme.color.white};
  box-shadow: 0px 3px 22px rgba(0, 0, 0, 0.16);
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;

  font-weight: bold;
  font-size: 1rem;
  color: ${props => props.theme.color.gray};
  z-index: 999;
`;
export const Nav = styled.div`
  width: 100%;
  max-width: 1400px;

  display: flex;
  justify-content: space-between;
  align-items: center;
`;
export const NavColumn = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;

export const TitleWrapper = styled.div`
  display: flex;
  margin: 0 40px;
  color: ${props => props.theme.color.primary};
  @media screen and (max-width: 790px) {
    margin: 0 20px;
  }
`;

export const MenuWrapper = styled.div`
  display: flex;
  li {
    margin: 0 18px;
  }

  user-select: none;
  -webkit-touch-callout: none;
  -webkit-user-select: none;
  -khtml-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;

  @media screen and (max-width: 750px) {
    display: none;
  }

  @media screen and (max-width: 768px) {
    li {
      margin: 0 14px;
    }
  }
`;

export const ButtonWrapper = styled.div`
  display: flex;
  align-items:center;
  margin: 0 20px;

  color: ${props => props.theme.color.primary};

  li {
    margin: 0px 18px;
  }

  @media screen and (max-width: 768px) {
    margin: 0 20px;
  }

  @media screen and (max-width: 750px) {
    display: none;
  }
`;

export const RegisterButton = styled.button`
  width: 80px;
  height: 40px;
  background: ${props => props.theme.color.primary};
  border-radius: 10px;
  color: ${props => props.theme.color.white};
  font-size: 1rem;

  cursor: pointer;

  @media screen and (max-width: 900px) {
    font-size: 0.9rem;
  }
`;

export const DrawerWrapper = styled.div`
  display: none;
  margin-right: 20px;

  @media screen and (max-width: 750px) {
    display: flex;
  }

`;

export const MobileIcon = styled.div`
  font-size: 20px;
  color: ${props => props.theme.color.deepBlue};
`;