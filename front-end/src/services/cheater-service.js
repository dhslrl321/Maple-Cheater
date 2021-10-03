import API from "../utils/api";

export const fetchCheater = async (accessToken, nickname) => {
  try {
    const { data, status } = await API.get(
      `/cheaters/${nickname}`, {
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