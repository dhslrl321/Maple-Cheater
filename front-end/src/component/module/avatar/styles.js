import styled from 'styled-components';

export const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
`;

export const AvatarWrapper = styled.div`
  width: 40px;
  height: 40px;
  border: 2px solid ${props => props.theme.color.gray};
  border-radius: 20px;
  object-fit: cover;

  @media screen and (max-width: 576px) {
    border: 1px solid ${props => props.theme.color.gray};
    img {
      width: 32px;
      height: 32px;
    }
  }
`;

export const IconWrapper = styled.div`
  margin-left: 8px;
  font-size: 18px;
  color: ${props => props.theme.color.gray};
  font-weight: bolder;
`;