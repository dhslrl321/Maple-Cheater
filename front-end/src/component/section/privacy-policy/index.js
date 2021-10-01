import React from 'react'

import Markdown from "../../module/markdown";
import { privacyPolicyData } from "./data";

const PrivacyPolicy = () => {
  return (
    <section>
      <Markdown textData={privacyPolicyData} />
    </section>
  )
}

export default PrivacyPolicy;
