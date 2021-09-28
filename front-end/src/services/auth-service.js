import API from "../utils/api";

export const fetchEmailVerification = async (email) => {
  try {
    const { data, status } = await API.get("/authenticate/email/" + email);
    return { data, status, error: null };
  } catch (e) {
    const { data: { message }, status } = e.response;
    return { data: message, status, error: e };
  };
};

export const fetchAuthenticateAuthCode = async (email, authCode) => {
  try {
    const { data, status } = await API.get("/authenticate/email/code/" + email + "/" + authCode);
    return { data, status, error: null };
  } catch (error) {
    const { data: { message }, status } = error.response;
    return { data: message, status, error };
  };
};

export const fetchSendTempPassword = async (email) => {
  try {
    const { data, status } = await API.get("/authenticate/password/" + email);
    return { data, status, error: null };
  } catch (error) {
    const { data: { message }, status } = error.response;
    return { data: message, status, error };
  };
};

export const fetchLogin = async (user) => {
  const { email, password } = user;

  const requestBody = {
    email,
    password
  };

  try {
    const { data, status } = await API.post("/authenticate", JSON.stringify(requestBody));
    return { data, status, error: null };
  } catch (error) {
    const { data: { message }, status } = error.response;
    return { data: message, status, error };
  };
};

export const validateUser = async (accessToken) => {
  try {
    const { data, status } = await API.get("/authenticate/validate"); // 헤더에 엑세스 토큰 넣어야함
    return { data, status, error: null };
  } catch (error) {
    const { data: { message }, status } = error.response;
    return { data: message, status, error };
  };
}