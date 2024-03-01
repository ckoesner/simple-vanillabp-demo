import {useEffect, useState} from 'react';
import { UserTaskForm } from '@vanillabp/bc-shared';

const TestForm2: UserTaskForm = ({ userTask }) => {
    const [ evaluationValue, setEvaluationValue ] = useState(1);
    const [ joke, setJoke ] = useState(1);

    useEffect(() => {
            fetch('/wm/demo/api/demo/' + userTask.businessId + '/task/' + userTask.id + '/score')
                .then((response) => response.json())
                .then((data) => {
                  console.log(data);
                    setJoke(data.joke);
                    setEvaluationValue(data.evaluation);
                })
                .catch((err) => {
                  console.error(err.message);
                });
    }, [ ]);


    const endProcess = (e) => {
        fetch('/wm/demo/api/demo/' + userTask.businessId + '/task/' + userTask.id + '/complete',
            {
                method: "POST"
            })
            .then(data => console.log(data))
            .then(window.close)
            .catch(error => console.error(error));
    };

    const evaluation = (evaluationNumber: number): string => {
        if(evaluationNumber == -1){
            return "Fehlgeschlagen.";
        } else {
            return evaluationNumber.toString() + "/10";
        }
    }

    return (
        <div>
            <h1>Bewertung deines Humors</h1>
            Du hast den folgenden Witz eingegeben:
            <br/>
            <br/>
            {joke}
            <br/>
            <br/>
            Die Bewertung deines Witzes: {evaluation(evaluationValue)}
            <br/>
            <button onClick={endProcess}>Abschließen</button>
        </div>);
};

export default TestForm2;
