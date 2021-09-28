import { fetchLogin, fetchValidateUser } from "../services/auth-service";
import * as T from "../constants/action-type";


const initialState = {
  user: {
    loading: false,
    data: null,
    status: null,
    error: null
  }
};

// reducer
export const reducer = (state = initialState, action) => {
  const { type, payload } = action;
  if (type === T.GET_USER) {
    return {
      ...state,
      user: {
        loading: true,
        data: null,
        status: null,
        error: null,
      }
    }
  } else if (type === T.GET_USER_SUCCESS) {
    const { data, status } = payload;
    return {
      ...state,
      user: {
        loading: false,
        data,
        status,
        error: null
      }
    }
  } else if (type === T.GET_USER_FAILURE) {
    const { data, status, error } = payload;
    return {
      ...state,
      user: {
        loading: false,
        data,
        status,
        error,
      }
    };
  } else if (type === T.CLEAR_USER) {
    return {
      ...state,
      user: {
        loading: false,
        data: null,
        status: null,
        error: null
      }
    }
  } else if (type === T.VALIDATE_USER) {
    return {
      ...state,
      user: {
        loading: true,
        data: null,
        state: null,
        error: null,
      }
    }
  } else if (type === T.VALIDATE_USER_SUCCESS) {
    const { data, status } = payload;
    return {
      ...state,
      user: {
        loading: false,
        data,
        status,
        error: null,
      }
    }
  } else if (type === T.VALIDATE_USER_FAILURE) {
    const { data, status, error } = payload;
    return {
      ...state,
      user: {
        loading: false,
        data,
        status,
        error,
      }
    }
  }
  else {
    return state;
  }
};

// action creator
export const getUser = (user) => async dispatch => {
  dispatch({ type: T.GET_USER });

  const { data, status, error } = await fetchLogin(user);
  if (error === null) {
    dispatch({
      type: T.GET_USER_SUCCESS,
      payload: {
        data,
        status
      },
    });
  } else {
    dispatch({
      type: T.GET_USER_FAILURE,
      payload: {
        data,
        status,
        error
      }
    })
  }
};

export const validateUser = (accessToken) => async dispatch => {
  dispatch({ type: T.VALIDATE_USER });

  const { data, status, error } = await fetchValidateUser(accessToken);
  if (error === null) {
    dispatch({
      type: T.VALIDATE_USER_SUCCESS,
      payload: {
        data,
        status
      },
    });
  } else {
    dispatch({
      type: T.VALIDATE_USER_FAILURE,
      payload: {
        data,
        status,
        error
      }
    })
  }
};

export const clearUser = () => dispatch => {
  dispatch({ type: T.CLEAR_USER, payload: {} });
}