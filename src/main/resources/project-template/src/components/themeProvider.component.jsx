import { ThemeProvider as MuiThemeProvider } from "@mui/material";

function ThemeProvider({ theme, children }) {
  return <MuiThemeProvider theme={theme}>{children}</MuiThemeProvider>;
}

export default ThemeProvider;
