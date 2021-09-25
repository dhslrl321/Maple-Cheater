import API from "../util/api";

export const login = async (body) => {
  try {
    const { data } = await API.post("/authenticate", JSON.stringify(body));
    return data;
  } catch {
    console.log("로그인 실패");
  }
};

export const register = async (body) => {
  try {
    const { data } = await API.post("/users", JSON.stringify(body));
    return data;
  } catch {
    console.log("회원가입 실패");
  }
};