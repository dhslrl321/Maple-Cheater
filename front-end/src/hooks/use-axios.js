import { useState, useEffect, useReducer } from 'react'

const LOADING = "useAxios/LOADING";
const SUCCESS = "useAxios/SUCCESS";
const FAILURE = "useAxios/FAILURE";

const reducer = (state, action) => {
  switch (action.type) {
    case LOADING:
      return {
        loading: true,
        data: null,
        error: null,
        status: null
      };
    case SUCCESS:
      return {
        loading: false,
        data: action.payload.data,
        error: null,
        status: action.payload.status
      };
    case FAILURE:
      return {
        loading: false,
        data: action.payload.data,
        error: action.payload.error,
        status: action.payload.status
      };
    default:
      throw new Error(`Unhandled action type: ${action.type}`)
  }
}

const useAxios = (callback, deps = [], skip = false) => {

  const [state, dispatch] = useReducer(reducer, {
    loading: false,
    data: null,
    error: false,
    status: null
  });

  const fetchData = async () => {
    dispatch({ type: LOADING });

    const { data, status, error } = await callback();

    if (error === null) { // 통신에 성공한 경우
      dispatch({
        type: SUCCESS,
        payload: { data, status }
      });
    } else { // 통신에 실패한 경우
      dispatch({
        type: FAILURE,
        payload: { data, status, error }
      });
    }
  }

  useEffect(() => {
    if (skip) return;
    fetchData();
  }, deps);

  return [state, fetchData];
}

export default useAxios;