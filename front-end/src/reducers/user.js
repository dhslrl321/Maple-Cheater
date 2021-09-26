import { SET_USER } from "./types";

const initialState = {

};

// reducer
export const reducer = (state = initialState, action) => {
  if (action.type === SET_USER) {
    const { user } = action.payload;
    return {
      user: user
    }
  } else {
    return state;
  }
};

// action creator
export const setUser = (user) => {
  return {
    type: SET_USER,
    user: {
      user,
    }
  }
}

// function dispatch
// export const loadAccessToken = (loginRequestData) => {
//   return async (dispatch) => {
//     const loginResponseData = await login(loginRequestData);

//     const { userId, email, nickname, accessToken } = loginResponseData;

//     console.log(accessToken);

//     const user = {
//       userId,
//       email,
//       nickname,
//       accessToken
//     };

//     dispatch(setUser(user));
//   }
// }