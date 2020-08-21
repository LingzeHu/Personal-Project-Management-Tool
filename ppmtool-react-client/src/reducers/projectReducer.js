import { GET_PROJECTS } from "../actions/types";

const initailState = {
  projects: [],
  project: {},
};

export default function (state = initailState, action) {
  switch (action.type) {
    case GET_PROJECTS:
      return {
        ...state,
        projects: action.payload,
      };

    default:
      return state;
  }
}