import API from "../utils/api";

export const fetchEvidenceByReportId = async (accessToken, reportId) => {
  try {
    const { data, status } = await API.get(
      `/evidences/${reportId}`, {
      headers: {
        Authorization: `Bearer ${accessToken}`
      }
    });
    return { data, status, error: null };
  } catch (error) {
    const { data: { message }, status } = error.response;
    return { data: message, status, error };;
  }
}

export const fetchUploadImage = async (accessToken, files) => {

  const formData = new FormData();
  files.map(file => formData.append("images", file));

  try {
    const { data, status } = await API.get(
      `/evidences/${reportId}`,
      formData, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
        "Content-Type": "multipart/form-data",
      }
    });
    return { data, status, error: null };
  } catch (error) {
    const { data: { message }, status } = error.response;
    return { data: message, status, error };;
  }
}