import { Typography as MuiTypography } from "@mui/material";

function Typography({ variant, text, modifiers, onClick }) {
  const getSplittedText = (text) => {
    return text.join("\n");
  };

  return (
      <MuiTypography variant={variant} style={modifiers} onClick={onClick}>
        {getSplittedText(text)}
      </MuiTypography>
  );
}

export default Typography;
