import React, { lazy } from 'react';
import { Text } from 'grommet';
import { taskDefinition as TestForm1_taskDefinition } from './TestForm1';
import { UserTaskForm as UserTaskFormComponent } from '@vanillabp/bc-shared';

const TestForm1 = lazy(() => import('./TestForm1/UserTaskForm'));

const UserTaskForm: UserTaskFormComponent = ({ userTask }) =>
    userTask.taskDefinition === TestForm1_taskDefinition
        ? <TestForm1 userTask={ userTask } />
        :  <Text>{ `unknown task '${userTask.taskDefinition}'` }</Text>;

export default UserTaskForm;
