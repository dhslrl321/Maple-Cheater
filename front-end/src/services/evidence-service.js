import API from "../utils/api";
import * as Storage from "../utils/storage";

export const fetchEvidenceByReportId = async (reportId) => {
  try {
    const { data, status } = await API.get(
      `/evidences/${reportId}`, {
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

export const fetchUploadImage = async (files, reportId) => {

  const formData = new FormData();
  files.map(file => formData.append("images", file));

  try {
    const { data, status } = await API.post(
      `/evidences/${reportId}`,
      formData, {
      headers: {
        Authorization: `Bearer ${Storage.getAccessToken()}`,
        "Content-Type": "multipart/form-data",
      }
    });
    return { data, status, error: null };
  } catch (error) {
    const { data: { message }, status } = error.response;
    return { data: message, status, error };;
  }
}