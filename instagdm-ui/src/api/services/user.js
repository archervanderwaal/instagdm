import api from "../api";

export const createThreads = (username, password) => {
  return api.post("/api/user/login", {
    username,
    password,
  });
};
