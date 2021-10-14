import axios from "axios";

const API = axios.create({
  baseURL: "http://maplecheater.com/api/v1",
  headers: {
    "Content-Type": "application/json",
  }
});

export default API;