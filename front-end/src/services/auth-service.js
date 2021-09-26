import API from "../utils/api";

export const fetchEmailVerification = async (email) => {
  const { data, status } = await API.get("/authenticate/" + email);
  return { data, status };
}

export const fetchAuthenticateAuthCode = async (email, authCode) => {
  const { data, status } = await API.get("/authenticate/" + email + "/" + authCode);
  return { data, status };
}