import { Button as MuiButton } from "@mui/material";

function Button({ text, onClick, variant }) {
  return (
    <MuiButton onClick={onClick} variant={variant}>
      {text}
    </MuiButton>
  );
}

export default Button;
