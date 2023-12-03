import api from '../api';

export const createThreads = (username, password, title, inviters) => {
  return api.post('/threads/create', {
    username,
    password,
    title,
    inviters,
  });
};