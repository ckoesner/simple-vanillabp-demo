const taskDefinition = 'jokeEvaluation';

const userTaskListColumns = [
  {
    id: 'good joke',
    title: {
      'de': 'Guter Witz?',
      'en': 'Good Joke?'
    },
    path: 'details.isGoodJoke',
    showAsColumn: true,
    sortable: true,
    filterable: true,
    width: '10rem',
    priority: 1
  }
];

export { userTaskListColumns, taskDefinition };
