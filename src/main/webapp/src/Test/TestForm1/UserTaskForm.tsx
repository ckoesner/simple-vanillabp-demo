import { useEffect, useState } from 'react';
import { UserTaskForm } from '@vanillabp/bc-shared';

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


    const sendMessage = () => {
        fetch('/wm/demo/api/demo/' + userTask.businessId + '/process-task-completed/' + userTask.id,
            {method: "GET"})
            .then(data => console.log(data))
            .catch(error => console.error(error));
        // fetch('http://localhost:8078/api/demo/' + userTask.businessId + '/process-task-completed/' + userTask.id,
        //     {method: "GET"})
        //     .then(data => console.log(data))
        //     .catch(error => console.error(error));

    };

    return (
      <div>
          <h1>Testformular: '{userTask.title.de}'</h1>
          Task ID: {userTask?.id ?? 'not available'}
          <br/>
          Business ID: {userTask?.businessId ?? 'not available'}
          <br/>
          User: {userDetails?.email ?? 'unknown'}
          <br/>
          <form onSubmit={event => sendMessage()}>
{/*              <label>
                  Name:
                  <input type="text" className="form-control" value={formText}
                         onChange={(e) => setFormText(e.target.value)}
                  />
              </label>*/}
              <input type="submit" value="Submit"/>
          </form>
      </div>);
};

export default TestForm1;
