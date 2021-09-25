import API from "../utils/api";

export const login = async (body) => {
  try {
    const { data } = await API.post("/authenticate", JSON.stringify(body));
    return data;
  } catch {
    console.log("로그인 실패");
  }
};

export const sendAuthCodeMail = async (email) => {
  try {
    const { status } = await API.get("/authenticate/" + email);
    if (status === 204) { // 통신 성공
      return true;
    }
  } catch (error) {
    console.log("회원가입 실패");
    console.log("error : ", error);
    return false;
  }
};

export const authenticateAuthCode = async (email, code) => {
  try {
    await API.get("/authenticate/" + email + "/" + code);
    return true;
  } catch {
    console.log("인증 코드 비교 실패");
    return false;
  }
}