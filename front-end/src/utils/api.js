import axios from "axios";

const API = axios.create({
  baseURL: process.env.NEXT_PUBLIC_BACKEND_IP + "/api/v1",
  headers: {
    "Content-Type": "application/json",
  }
});

export default API;