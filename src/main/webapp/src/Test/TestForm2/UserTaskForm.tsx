import {useEffect, useState} from 'react';
import { UserTaskForm } from '@vanillabp/bc-shared';

const TestForm2: UserTaskForm = ({ userTask }) => {
    const [ formText, setFormText ] = useState("");

    useEffect(() => {
            fetch('/wm/demo/api/demo/' + userTask.businessId + '/task/' + userTask.id + '/score')
                .then((response) => response.json())
                .then((data) => {
                  console.log(data);
                  setFormText(data);
                })
                .catch((err) => {
                  console.error(err.message);
                });
    }, [ ]);


    const endProcess = (e) => {
        console.log(formText)
        fetch('/wm/demo/api/demo/' + userTask.businessId + '/task/' + userTask.id + '/complete',
            {
                method: "POST"
            })
            .then(data => console.log(data))
            .then(window.close)
            .catch(error => console.error(error));
    };

    return (
      <div>
          <h1>Your joke evaluation</h1>
          Task ID: {userTask?.id ?? 'not available'}
          <br/>
          Business ID: {userTask?.businessId ?? 'not available'}
          <br/>
          Task evaluation: {formText}
          <br />
          <button onClick={endProcess}>Finish</button>
      </div>);
};

export default TestForm2;
