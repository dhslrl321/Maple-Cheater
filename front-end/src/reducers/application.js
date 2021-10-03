import * as T from "../constants/action-type";

const initialState = {
  alert: {

  }
};

export const reducer = (state = initialState, action) => {
  const { type } = action;
  if (type === T.ENABLE_ALERT) {
    const { severity, title, message } = action.payload;
    return {
      ...state,
      alert: {
        show: true,
        severity,
        title,
        message
      }
    }
  } else if (type === T.DISABLE_ALERT) {
    return {
      ...state,
      alert: {
        show: false,
      }
    }
  } else {
    return state;
  }
};

export const showAlert = (content) => dispatch => {
  const { title, message, severity } = content;
  const payload = {
    title,
    message,
    severity,
  }
  dispatch({ type: T.ENABLE_ALERT, payload });
}

export const disableAlert = () => dispatch => {
  const payload = {
    show: true,
  }
  dispatch({ type: T.DISABLE_ALERT, payload });
}