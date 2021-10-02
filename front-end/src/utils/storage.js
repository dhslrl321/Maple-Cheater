export const getUser = () => {
  const data = window.localStorage.getItem("user");
  if (!data) {
    return null;
  }

  return JSON.parse(data);
}

export const setUser = (user) => {
  window.localStorage.setItem("user", JSON.stringify(user));
}

export const setAccessToken = (accessToken) => {
  window.localStorage.setItem("accessToken", JSON.stringify(accessToken));
}

export const getAccessToken = () => {
  const accessToken = window.localStorage.getItem("accessToken");
  if (!accessToken) {
    return null;
  }

  return JSON.parse(accessToken);
}

export const clearAll = () => {
  window.localStorage.removeItem("accessToken");
  window.localStorage.removeItem("user");
}

export const clearUser = () => {
  window.localStorage.removeItem("user");
}

export const clearAccessToken = () => {
  window.localStorage.removeItem("accessToken");
}