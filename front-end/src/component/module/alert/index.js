import { useState } from 'react'

import Snackbar from "@mui/material/Snackbar";
import Alert from "@mui/material/Alert";
import AlertTitle from "@mui/material/AlertTitle";

const index = ({ isOpen, severity = "success", handleAlertClose, title, message }) => {
  return (
    <>
      <Snackbar
        anchorOrigin={{
          vertical: "top",
          horizontal: "right"
        }}
        open={isOpen}
        autoHideDuration={2000}
        onClose={handleAlertClose}
      >
        <Alert
          variant="filled"
          severity={severity}
          open={isOpen}
          onClose={handleAlertClose}>
          <AlertTitle>{title}</AlertTitle>
          <strong>{message}</strong>
        </Alert>
      </Snackbar>
    </>
  );
}


export default index
