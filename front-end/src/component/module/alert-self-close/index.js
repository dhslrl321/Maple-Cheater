import { useState } from 'react'

import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert";
import AlertTitle from "@mui/material/AlertTitle";

const index = ({ severity, title, message }) => {

  const [open, setOpen] = useState(true);

  const handleClose = () => {
    setOpen(false);
  }

  return (
    <>
      <Snackbar
        anchorOrigin={{
          vertical: "top",
          horizontal: "right"
        }}
        open={open}
        autoHideDuration={2000}
        onClose={handleClose}
      >
        <Alert
          variant="filled"
          severity={severity}
          open={open}
          onClose={handleClose}>
          <AlertTitle>{title}</AlertTitle>
          <strong>{message}</strong>
        </Alert>
      </Snackbar>
    </>
  );
}


export default index
