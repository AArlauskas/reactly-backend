import {
  ListItemText as MuiListItemText,
  ListItem as MuiListItem,
  ListItemButton as MuiListItemButton,
} from "@mui/material";

function ListItemText({ primaryText, secondaryText, modifiers, onClick }) {
  return (
    <MuiListItem disablePadding>
      <MuiListItemButton onClick={onClick}>
        <MuiListItemText
          primary={primaryText}
          secondary={secondaryText}
          style={modifiers}
        />
      </MuiListItemButton>
    </MuiListItem>
  );
}

export default ListItemText;
