import Link from 'next/link';

import Box from '@mui/material/Box';
import SwipeableDrawer from '@mui/material/SwipeableDrawer';
import List from '@mui/material/List';
import Divider from '@mui/material/Divider';
import ListItem from '@mui/material/ListItem';

import * as S from "./styles";

const index = ({ open, toggle }) => {

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
              <Link href="/guide">
                <ListItem button>이용 규칙</ListItem>
              </Link>
            </S.LinkColumn>
          </List>
          <Divider />
          <List>
            <S.AuthLinkColumn>
              <Link href="/login">
                <ListItem button>로그인</ListItem>
              </Link>
              <Link href="/register">
                <ListItem button>회원가입</ListItem>
              </Link>
            </S.AuthLinkColumn>
          </List>
        </Box>
      </SwipeableDrawer>
    </div>
  );
}

export default index;