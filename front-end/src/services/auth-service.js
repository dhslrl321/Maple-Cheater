import API from "../utils/api";

export const fetchEmailVerification = async (email) => {
  await API.get("/authenticate/" + email);
}