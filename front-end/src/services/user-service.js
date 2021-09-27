import API from "../utils/api";

export const fetchRegister = async (user) => {
  const { email, password, nickname } = user;

  const requestBody = {
    email,
    password,
    nickname
  };

  try {
    const { data, status } = await API.post("/users", JSON.stringify(requestBody));
    return { data, status, error: null };
  } catch (error) {
    const { data: { message }, status } = error.response;
    return { data: message, status, error };
  }
}

