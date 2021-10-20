export const emailValidator = (email) => {
  const regex = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  return regex.test(String(email).toLowerCase());
}

export const tokenValidator = (token) => {
  const regex = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;
  return regex.test(token);
}

export const whitespaceValidator = (text) => {
  const regex = /\s/;
  return regex.test(text);
}

export const searchValidator = (text) => {
  const regex = /^[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9|]{2,30}$/;
  return regex.test(text);
}

export const emptyTextValidator = (text) => {
  return text !== ""
}

export const passwordValidator = (password) => {
  const regex = /^[a-zA-Z0-9]{8,15}$/;
  return regex.test(password);
}

export const situationValidator = (text) => {
  const regex = /^[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9|\s\n|~!@#$%^&*()+=.,]{10,300}$/;
  return regex.test(text);
}

export const nicknameValidator = (nickname) => {
  const regex = /^[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9]{2,8}$/;
  return regex.test(nickname);
}