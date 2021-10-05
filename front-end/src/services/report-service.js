import API from "../utils/api";
import * as Storage from "../utils/storage";

export const fetchAddReport = async (userId, reportData, files) => {
  const formData = new FormData();

  files.map(file => formData.append("images", file));

  formData.append("ingameNickname", reportData.nickname);
  formData.append("year", reportData.yearSelect);
  formData.append("month", reportData.monthSelect);
  formData.append("day", reportData.daySelect);
  formData.append("situation", reportData.situation);
  formData.append("userId", userId);
  formData.append("ingameServer", reportData.serverSelect);
  formData.append("cheatingType", reportData.cheatingTypeSelect);

  try {
    const { data, status } = await API.post(
      `/reports`,
      formData, {
      headers: {
        Authorization: `Bearer ${Storage.getAccessToken()}`,
        "Content-Type": "multipart/form-data",
      },
    });
    return { data, status, error: null };
  } catch (error) {
    const { data: { message }, status } = error.response;
    return { data: message, status, error };;
  }
}