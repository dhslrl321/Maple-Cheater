import API from "../utils/api";
import * as Storage from "../utils/storage";

export const fetchAddReport = async (reportData, userId) => {
  const request = {
    ingameNickname: reportData.nickname,
    cheatingDatetime: String(reportData.yearSelect) + "-" + String(reportData.monthSelect) + "-" + String(reportData.daySelect) + "T00:00:00",
    situation: reportData.situation,
    userId: userId,
    ingameServer: reportData.serverSelect,
    cheatingType: reportData.cheatingTypeSelect,
  };

  try {
    const { data, status } = await API.post(
      `/reports`,
      JSON.stringify(request), {
      headers: {
        Authorization: `Bearer ${Storage.getAccessToken()}`
      },
    });
    return { data, status, error: null };
  } catch (error) {
    const { data: { message }, status } = error.response;
    return { data: message, status, error };;
  }
}