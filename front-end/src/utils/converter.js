import Chip from '@mui/material/Chip';

export const convertToClip = (status, isSmall) => {
  if (status === "PENDING") {
    return <Chip color="default" label="확인중" size={isSmall ? "small" : ""} />
  } else if (status === "REJECTED") {
    return <Chip label="거절됨" color="error" size={isSmall ? "small" : ""} />
  } else {
    return <Chip label="등록 완료" color="success" size={isSmall ? "small" : ""} />
  }
}