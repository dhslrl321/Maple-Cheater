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
        data: action.data,
        error: null,
        status: action.status
      };
    case FAILURE:
      return {
        loading: false,
        data: null,
        error: action.error,
        status: action.status
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
    try {
      const { data, status } = await callback();
      dispatch({ type: SUCCESS, data, status });
    } catch (e) {
      dispatch({ type: FAILURE, error: e, status: e.response.status });
    }
  }

  useEffect(() => {
    if (skip) return;
    fetchData();
  }, deps);

  return [state, fetchData];
}

export default useAxios;