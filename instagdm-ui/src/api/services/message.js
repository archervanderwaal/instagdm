import api from "../api";

export const sendMessage = (username, password, message, receivers, threadIds) => {
  return api.post("/ins/direct/message/send", {
    username,
    password,
    message,
    receivers,
    threadIds,
  });
};
