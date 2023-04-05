import { List as MuiList } from "@mui/material";

function List({ children, modifiers }) {
  return <MuiList style={modifiers}>{children}</MuiList>;
}

export default List;
