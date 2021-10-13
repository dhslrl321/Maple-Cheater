import Link from 'next/link';
import { useSelector } from "react-redux";

import * as S from "./styles";

import Box from '@mui/material/Box';
import SwipeableDrawer from '@mui/material/SwipeableDrawer';
import List from '@mui/material/List';
import Divider from '@mui/material/Divider';
import ListItem from '@mui/material/ListItem';

import Avatar from "../avatar";

const index = ({ open, toggle }) => {

  const { data, status } = useSelector(state => state.userReducer.user);

  return (
    <div>
      <SwipeableDrawer
        anchor="right"
        open={open}
        onClose={toggle}
        onOpen={toggle}
      >
        <Box
          sx={{ width: 250 }}
          role="presentation"
          onClick={toggle}
          onKeyDown={toggle}
        >
          <List>
            <S.DrawerTitle>
              <ListItem button>
                Maple-Cheater
              </ListItem>

            </S.DrawerTitle>
          </List>
          <Divider />
          <List>
            <S.LinkColumn>
              <Link href="/">
                <ListItem button>홈</ListItem>
              </Link>
            </S.LinkColumn>
            <S.LinkColumn>
              <Link href="/search">
                <ListItem button>사용자 검색</ListItem>
              </Link>
            </S.LinkColumn>
            <S.LinkColumn>
              <Link href="/report">
                <ListItem button>피해 등록</ListItem>
              </Link>
            </S.LinkColumn>
            <S.LinkColumn>
              <Link href="/guideline">
                <ListItem button>이용 규칙</ListItem>
              </Link>
            </S.LinkColumn>
          </List>
          <Divider />
          <List>
            {status === 200 ? (
              <>
                <S.AvatarWrapper>
                  <Avatar />
                  <S.Nickname>{data.nickname}</S.Nickname>
                  <S.Email>{data.email}</S.Email>
                </S.AvatarWrapper>
                <Divider />
                <S.LinkColumn>
                  <Link href={`/users/[userId]/reports`} as={`/users/${data.userId}/reports`}>
                    <ListItem button>내 신고 목록</ListItem>
                  </Link>
                </S.LinkColumn>
                <S.LinkColumn>
                  <Link href="/users/password">
                    <ListItem button>비밀번호 변경</ListItem>
                  </Link>
                </S.LinkColumn>
                <S.LinkColumn>
                  <Link href="/logout">
                    <ListItem button>로그아웃</ListItem>
                  </Link>
                </S.LinkColumn>
              </>
            ) : (
                <S.AuthLinkColumn>
                  <Link href="/login">
                    <ListItem button>로그인</ListItem>
                  </Link>
                  <Link href="/register">
                    <ListItem button>회원가입</ListItem>
                  </Link>
                </S.AuthLinkColumn>
              )}
          </List>
        </Box>
      </SwipeableDrawer>
    </div>
  );
}

export default index;