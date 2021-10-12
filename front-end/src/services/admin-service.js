import API from "../utils/api";
import * as Storage from "../utils/storage";

export const fetchAllReports = async () => {
  try {
    const { data, status } = await API.get(
      `/reports`, {
      headers: {
        Authorization: `Bearer ${Storage.getAccessToken()}`
      }
    });
    return { data, status, error: null };
  } catch (error) {
    const { data: { message }, status } = error.response;
    return { data: message, status, error };;
  };
};

export const fetchReportById = async (reportId) => {
  try {
    const { data, status } = await API.get(
      `/reports/${reportId}`, {
      headers: {
        Authorization: `Bearer ${Storage.getAccessToken()}`
      }
    });
    return { data, status, error: null };
  } catch (error) {
    const { data: { message }, status } = error.response;
    return { data: message, status, error };;
  }
};

export const fetchAddCheater = async (report, cheatingType, ingameServer) => {
  const request = {
    ingameNickname: report.cheaterNickname,
    ingameServer,
    cheatingType,
    situation: report.situation,
    cheatingDatetime: report.cheatingDatetime
  };

  console.log(request);

  try {
    const { data, status } = await API.post(
      "/cheaters", JSON.stringify(request), {
      headers: {
        Authorization: `Bearer ${Storage.getAccessToken()}`
      }
    });
    return { data, status, error: null };
  } catch (error) {
    const { data: { message }, status } = error.response;
    return { data: message, status, error };;
  }
}

export const fetchUpdateReportStatus = async (reportId, reject) => {
  const request = {
    accepted: reject ? false : true
  };

  console.log(request);

  try {
    const { data, status } = await API.patch(
      `/reports/${reportId}`, JSON.stringify(request), {
      headers: {
        Authorization: `Bearer ${Storage.getAccessToken()}`
      }
    });
    return { data, status, error: null };
  } catch (error) {
    console.log(error);
    const { data: { message }, status } = error.response;
    return { data: message, status, error };;
  }
}