import styled from 'styled-components';

export const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
`;

export const TableWrapper = styled.div`
  width: 80%;
  background: ${props => props.theme.color.white};
  height: 600px;
  border-radius: 15px;
`;