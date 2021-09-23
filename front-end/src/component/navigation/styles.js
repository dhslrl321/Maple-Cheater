import styled from 'styled-components';

export const Container = styled.nav`
  width: 100%;
  height: 60px;
  background: ${props => props.theme.color.white};
  box-shadow: 0px 3px 22px rgba(0, 0, 0, 0.16);
  font-size: 1rem;
  display: flex;
  justify-content: space-between;
  align-items: center;

  font-weight: bold;

  color: ${props => props.theme.color.gray};
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
`;

export const MenuWrapper = styled.div`
  display: flex;
  li {
    margin: 0 18px;
  }
`;

export const ButtonWrapper = styled.div`
  display: flex;
  align-items:center;
  margin: 0 40px;

  color: ${props => props.theme.color.primary};

  li {
    margin: 0px 18px;
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
`;