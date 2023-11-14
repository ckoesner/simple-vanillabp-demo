import { lazy } from 'react';
import { UserTaskListCell, WarningListCell } from '@vanillabp/bc-shared';
import { taskDefinition as TestForm1_taskDefinition } from './TestForm1';

const TestForm1_UserTaskListCell = lazy(() => import('./TestForm1/UserTaskList'));

const TaskListCell: UserTaskListCell = ({
    item,
    column,
defaultCell
}) =>
item.data.taskDefinition === TestForm1_taskDefinition
    ? <TestForm1_UserTaskListCell item={ item } column={ column } defaultCell={ defaultCell } />
    : <WarningListCell message={ `unknown task '${item.data.taskDefinition}'` } />;

export default TaskListCell;
