export const createPromiseThunk = (type, promiseCreator) => {
  const [SUCCESS, ERROR] = [`${type}_SUCCESS`, `${type}_FAILURE`];

  return param => async dispatch => {
    dispatch({ type, param });
    const { data, error, status } = await promiseCreator(param);
  }
}