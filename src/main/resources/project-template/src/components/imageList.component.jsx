import { ImageList as MuiImageList } from "@mui/material";

function ImageList({ cols, modifiers, children }) {
  return (
    <MuiImageList cols={cols} modifiers={modifiers}>
      {children}
    </MuiImageList>
  );
}

export default ImageList;
