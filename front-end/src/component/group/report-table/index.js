import React from 'react'
import { useSelector } from "react-redux";
import { useRouter } from "next/router";
import Link from "next/link";

import * as S from "./styles";

import useMediaQuery from '@mui/material/useMediaQuery';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Chip from '@mui/material/Chip';


const convertToClip = (status, isSmall) => {
  if (status === "PENDING") {
    return <Chip color="default" label="확인중" size={isSmall ? "small" : ""} />
  } else if (status === "REJECTED") {
    return <Chip variant="outlined" label="거절" color="error" size={isSmall ? "small" : ""} />
  } else {
    return <Chip variant="outlined" label="등록" color="success" size={isSmall ? "small" : ""} />
  }
}

const ReportTable = ({ reports }) => {

  const { data } = useSelector(state => state.userReducer.user);

  const isSmall = useMediaQuery("(max-width: 567px)");
  if (reports.length === 0) {
    return (<S.EmptyDetailText>내가 신고한 이력이 존재하지 않습니다!</S.EmptyDetailText>)
  }

  return (
    <TableContainer >
      <Table sx={{ minWidth: 200 }} aria-label="simple table">
        <TableHead>
          <TableRow>
            <TableCell align="left"><S.Title>신고자</S.Title></TableCell>
            <TableCell align="right"><S.Title>접수 현황</S.Title></TableCell>
            <TableCell align="right"><S.Title>신고 유형</S.Title></TableCell>
            <TableCell align="right"><S.Title>신고 시간</S.Title></TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {reports.map((report, index) => (
            <Link
              key={report.reportId}
              href="/users/[userId]/reports/[reportId]"
              as={`/users/${data && data.userId}/reports/${report.reportId}`}>
              <TableRow
                sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                hover
                style={{ cursor: "pointer" }}
              >
                <TableCell align="left" scope="row">
                  <S.Text>{report.reporterNickname}</S.Text>
                </TableCell>
                <TableCell align="right">
                  <S.Text>
                    {report.cheatingType}
                  </S.Text>
                </TableCell>
                <TableCell align="right">
                  <S.Text>
                    {convertToClip(report.status, isSmall)}
                  </S.Text>
                </TableCell>
                <TableCell align="right">
                  <S.Text>
                    {report.registeredAt.substr(0, 10)}
                  </S.Text>
                </TableCell>
              </TableRow>
            </Link>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  )
}

export default ReportTable
