import { fetchCheater } from "../services/cheater-service";
import * as T from "../constants/action-type";

const initialState = {
  search: {
    nickname: ""
  },
  cheater: {
    loading: false,
    data: null,
    status: null,
    error: null
  }
}

export const reducer = (state = initialState, action) => {
  const { type, payload } = action;
  if (type === T.GET_CHEATER) {
    return {
      ...state,
      cheater: {
        loading: true,
        data: null,
        status: null,
        error: null
      }
    }
  } else if (type === T.GET_CHEATER_SUCCESS) {
    const { data, status, nickname } = payload;
    return {
      ...state,
      search: {
        nickname
      },
      cheater: {
        loading: false,
        data,
        status,
        error: null
      }
    }
  } else if (type === T.GET_CHEATER_FAILURE) {
    const { data, status, error } = payload;
    return {
      ...state,
      cheater: {
        loading: false,
        data,
        status,
        error
      }
    }
  } else if (type === T.SET_SEARCH_NICKNAME) {
    const { nickname } = payload;
    return {
      ...state,
      search: {
        nickname
      }
    }
  }
  else {
    return state;
  }
}

export const getCheater = (nickname) => async dispatch => {
  dispatch({ type: T.GET_CHEATER });

  const { data, status, error } = await fetchCheater(nickname);

  if (error === null) {
    dispatch({
      type: T.GET_CHEATER_SUCCESS,
      payload: {
        data,
        status,
        nickname
      }
    })
  } else {
    dispatch({
      type: T.GET_CHEATER_FAILURE,
      payload: {
        data,
        status,
        error
      }
    })
  }
}

export const setSearchNickname = (nickname) => dispatch => {
  const payload = { nickname: nickname };
  dispatch({ type: T.SET_SEARCH_NICKNAME, payload });
}