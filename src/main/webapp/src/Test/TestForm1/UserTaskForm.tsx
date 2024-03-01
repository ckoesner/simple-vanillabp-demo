import {useState} from 'react';
import {UserTaskForm} from '@vanillabp/bc-shared';

const TestForm1: UserTaskForm = ({ userTask }) => {
    const [ formText, setFormText ] = useState("");

    const sendMessage = (e) => {
        console.log(formText)
        fetch('/wm/demo/api/demo/' + userTask.businessId + '/task/' + userTask.id + '/update',
            {
                method: "POST",
                body: formText
            })
            .then(data => console.log(data))
            .then(window.close)
            .catch(error => console.error(error));
    };

    return (
      <div>
          <h1>Write your joke now!</h1>
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
