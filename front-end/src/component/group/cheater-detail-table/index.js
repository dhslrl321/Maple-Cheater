import React from 'react'

import * as S from "./styles";

import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';


const CheateDetailTable = ({ cheaterNickname, histories, isSmall }) => {
  if (histories.length === 0) {
    return (<S.EmptyDetailText>{cheaterNickname} 으로 신고된 이력이 존재하지 않습니다!</S.EmptyDetailText>)
  }
  return (
    <TableContainer >
      <Table sx={{ minWidth: 200 }} aria-label="simple table">
        <TableHead>
          <TableRow>
            <TableCell align="left"><S.Title>사기 유형</S.Title></TableCell>
            <TableCell align="right"><S.Title>상황 요약</S.Title></TableCell>
            <TableCell align="right"><S.Title>거래 시간</S.Title></TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {histories.map((history, index) => (
            <TableRow
              key={index}
              sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
            >
              <TableCell align="left" scope="row">
                <S.Text>{history.cheatingType}</S.Text>
              </TableCell>
              <TableCell align="right">
                <S.Text>
                  {history.situation}
                </S.Text>
              </TableCell>
              <TableCell align="right">
                <S.Text>
                  {history.cheatingDatetime.substr(0, 10)}
                </S.Text>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  )
}

export default CheateDetailTable
