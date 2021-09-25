import { createTheme } from "@mui/material/styles";

const muiTheme = createTheme({
  palette: {
    primary: {
      main: '#5F56EF'
    },
    secondary: {
      main: '#5F56EF'
    },
    error: {
      main: '#009688'
    },
    text: {
      primary: '#525463',
      secondary: '#9E9E9E',
      disabled: '#4caf50',
      hint: '#000',
      myTextColor: '#000'
    }
  },
});

export default muiTheme;