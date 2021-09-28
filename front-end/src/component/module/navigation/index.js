import { useState, useEffect } from "react";
import { useSelector } from "react-redux";

import * as S from "./styles";

import Link from "next/link";

import { FaBars } from "react-icons/fa";

import Avatar from "../avatar";
import Drawer from "../drawer";
import Dropdown from "../dropdown";

import * as Storage from "../../../utils/storage";


const index = () => {
  const [user, setUser] = useState();
  const [open, setOpen] = useState(false);
  const [dropdown, setDropdown] = useState(false);

  const toggle = () => {
    setOpen(!open);
  };

  const handleDropdownOver = () => {
    setDropdown(true);
  }

  const handleDropdownLeave = () => {
    setDropdown(false);
  }

  const { data, status } = useSelector(state => state.userReducer.user);

  useEffect(() => {
    const user = Storage.getUser();
    setUser(user);
  }, []);

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

            {status === 200 || user !== null ? (
              <S.AvatarWrapper
                onMouseOver={handleDropdownOver}
                onMouseLeave={handleDropdownLeave}>
                <Avatar isNavigation />
                <Dropdown dropdown={dropdown} />
              </S.AvatarWrapper>
            ) : (
                <>
                  <li>
                    <Link href="/login">로그인</Link>
                  </li>
                  <li>
                    <Link href="/register">
                      <S.RegisterButton>회원가입</S.RegisterButton>
                    </Link>
                  </li>
                </>
              )}
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
