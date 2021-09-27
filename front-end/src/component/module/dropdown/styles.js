import styled from 'styled-components';

export const Container = styled.nav`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  width: 110px;
  height: 150px;
  background: white;
  position: absolute;
  top: 50px;
  box-shadow: 0px 3px 22px rgba(0, 0, 0, 0.16);
  border-radius: 10px;
  display: ${({ dropdown }) => dropdown ? "flex" : "none"};
`;

export const LinkWrapper = styled.div`
  margin: 10px;
  width: 100%;
  color: ${props => props.theme.color.gray};
  padding-left: 15px;
  font-size: 0.9rem;
`;
