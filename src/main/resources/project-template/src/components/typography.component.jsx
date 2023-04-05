import { Typography as MuiTypography } from "@mui/material";

function Typography({ variant, text, modifiers, onClick }) {
  return (
    <MuiTypography variant={variant} style={modifiers} onClick={onClick}>
      {text}
    </MuiTypography>
  );
}

export default Typography;
