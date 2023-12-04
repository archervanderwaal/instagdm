import axios from "axios";

const service = axios.create({
  baseURL: "https://api.archer.top/api/ins", // API的基础路径
  timeout: 100000, // 请求超时时间
});

export default service;
