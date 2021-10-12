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

export const convertTypeStringToId = (type, typeString) => {
  if (type === "ingameServer") {
    switch (typeString) {
      case "스카니아":
        return 1
      case "베라":
        return 2
      case "루나":
        return 3
      case "제니스":
        return 4
      case "크로아":
        return 5
      case "유니온":
        return 6
      case "엘리시움":
        return 7
      case "이노시스":
        return 8
      case "레드":
        return 9
      case "오로라":
        return 10
      case "아케인":
        return 11
      case "노바":
        return 12
      case "리부트":
        return 13
      case "리부트2":
        return 14
    }
  } else if (type === "cheatingType") {
    switch (typeString) {
      case "현금 거래":
        return 1
      case "주문서 거래":
        return 2
      case "사냥터 비매너":
        return 3
    }
  }
}