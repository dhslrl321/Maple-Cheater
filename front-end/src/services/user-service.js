import API from "../utils/api";
import * as Storage from "../utils/storage";

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

export const fetchMyReportList = async (accessToken, userId) => {
  try {
    const { data, status } = await API.get(
      `/users/${userId}/reports`, {
      headers: {
        Authorization: `Bearer ${accessToken}`
      }
    });
    return { data, status, error: null };
  } catch (e) {
    const { data: { message }, status } = e.response;
    return { data: message, status, error: e };
  };
};

export const fetchMyReportDetail = async (userId, reportId) => {
  console.log(Storage.getAccessToken());
  try {
    const { data, status } = await API.get(
      `/users/${userId}/reports/${reportId}`, {
      headers: {
        Authorization: `Bearer ${Storage.getAccessToken()}`
      }
    });
    return { data, status, error: null };
  } catch (e) {
    const { data: { message }, status } = e.response;
    return { data: message, status, error: e };
  };
};

