import api from "../api";

export const collectFollowers = (username, password, insUser) => {
  return api.post(`/ins/followers/${insUser}`, {
    username,
    password,
  });
};

export const listFollowers = () => {
  return api.get("/ins/followers/list");
};
