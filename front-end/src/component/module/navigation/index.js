import Link from "next/link";

import { FaBars } from "react-icons/fa";

import Avatar from "../avatar";
import Drawer from "../drawer";

import * as S from "./styles";
import { useState } from "react";

const index = () => {

  const [open, setOpen] = useState(false);
  const toggle = () => {
    setOpen(!open);
  };

  return (
    <S.Back>
      <S.Container>
        <S.Nav>
          <S.NavColumn>
            <S.TitleWrapper>
              <Link href="/">Maple-Cheater</Link>
            </S.TitleWrapper>
            <S.MenuWrapper>
              <li>
                <Link href="/cheater">사용자 검색</Link>
              </li>
              <li>
                <Link href="/report">피해 등록</Link>
              </li>
              <li>
                <Link href="/guide">이용 규칙</Link>
              </li>
            </S.MenuWrapper>
          </S.NavColumn>
          <S.ButtonWrapper>
            <li><Link href="/login">로그인</Link></li>
            <li>
              <Link href="/register">
                <S.RegisterButton>회원가입</S.RegisterButton>
              </Link>
            </li>
            {/* <Avatar /> auth 진행해야함 */}
          </S.ButtonWrapper>
          <S.DrawerWrapper>
            <S.MobileIcon>
              <FaBars onClick={toggle} />
            </S.MobileIcon>
            <Drawer open={open} toggle={toggle} />
          </S.DrawerWrapper>
        </S.Nav>
      </S.Container>
    </S.Back>
  )
}

export default index;
