import api from "../api";

export const createThreads = (username, password, title, inviters) => {
  return api.post("/ins/threads/create", {
    username,
    password,
    title,
    inviters,
  });
};

export const listThreads = () => {
  return api.get("/ins/threads/list");
};
