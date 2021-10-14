import axios from "axios";

const API = axios.create({
  baseURL: "http://133.186.159.111/api/v1",
  headers: {
    "Content-Type": "application/json",
  }
});

export default API;