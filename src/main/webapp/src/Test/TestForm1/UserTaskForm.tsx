import { useEffect, useState } from 'react';
import { UserTaskForm } from '@vanillabp/bc-shared';
import {TextInput} from "grommet";

const TestForm1: UserTaskForm = ({ userTask }) => {
    const [ userDetails, setUserDetails ] = useState();
    const [ formText, setFormText ] = useState("");

    useEffect(() => {
        if (userDetails !== undefined) {
          return;
        }
    //     fetch('/wm/demo/api/user-info')
    //         .then((response) => response.json())
    //         .then((data) => {
    //           console.log(data);
    //           setUserDetails(data);
    //         })
    //         .catch((err) => {
    //           console.error(err.message);
    //         });
    }, [ ]);

    const sendMessage = (e) => {
        console.log(formText)
        fetch('/wm/demo/api/demo/' + userTask.businessId + '/task/' + userTask.id + '/update',
            {
                method: "POST",
                body: formText
            })
            .then(data => console.log(data))
            .catch(error => console.error(error));
    };

    return (
      <div>
          <h1>Testformular: '{userTask.title.de}'</h1>
          Task ID: {userTask?.id ?? 'not available'}
          <br/>
          Business ID: {userTask?.businessId ?? 'not available'}
          <br/>

            <textarea onChange={e => setFormText(e.target.value)} placeholder="Write your joke now!">
            </textarea>

          <button onClick={sendMessage}>Send your joke</button>
      </div>);
};

export default TestForm1;
