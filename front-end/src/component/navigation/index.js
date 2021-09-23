import * as S from "./styles";
import Link from "next/link";

const index = () => {
  return (
    <S.Container>
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
            <Link href="/">삭제 요청</Link>
          </li>
        </S.MenuWrapper>
      </S.NavColumn>
      <S.ButtonWrapper>
        <li><Link href="#">로그인</Link></li>
        <li>
          <S.RegisterButton>회원가입</S.RegisterButton>
        </li>
      </S.ButtonWrapper>
    </S.Container>
  )
}

export default index;
