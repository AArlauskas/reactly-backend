import { AppBar, Box, Toolbar } from "@mui/material";

function TopBar({ children, modifiers }) {
  return (
    <Box sx={{ flexGrow: 1 }} style={modifiers}>
      <AppBar position="static">
        <Toolbar>{children}</Toolbar>
      </AppBar>
    </Box>
  );
}

export default TopBar;
