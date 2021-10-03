import { DropzoneArea } from "material-ui-dropzone";

const FileDropzone = ({ filesLimit, handleOnFileUpload }) => {
  const accept = ['image/jpeg', 'image/png'];

  return <DropzoneArea
    acceptedFiles={accept}
    dropzoneText="증거 이미지를 드래그해주세요"
    filesLimit={filesLimit}
    onChange={handleOnFileUpload} />
}

export default FileDropzone
