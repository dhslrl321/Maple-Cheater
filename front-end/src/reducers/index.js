import { createStore, combineReducers, applyMiddleware } from "redux";
import thunk from "redux-thunk";
import { composeWithDevTools } from "redux-devtools-extension";

import { reducer as userReducer } from "./user";
import { reducer as cheaterReducer } from "./cheater";
import { reducer as applicationReducer } from "./application";

const rootReducer = combineReducers({
  userReducer,
  cheaterReducer,
  applicationReducer,
});

const store = createStore(
  rootReducer,
  composeWithDevTools(applyMiddleware(thunk))
);

export default store;