import React from 'react'

import Markdown from "../../module/markdown";
import { userGuideline } from "./data";

const Guideline = () => {
  return (
    <section>
      <Markdown textData={userGuideline} />
    </section>
  )
}

export default Guideline;
