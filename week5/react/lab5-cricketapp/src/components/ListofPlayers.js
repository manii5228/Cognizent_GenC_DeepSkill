import React from 'react';

// ES6: Array of 11 players with name and score using map
function ListofPlayers() {
  const players = [
    { name: 'Virat Kohli', score: 82 },
    { name: 'Rohit Sharma', score: 65 },
    { name: 'Shubman Gill', score: 95 },
    { name: 'KL Rahul', score: 45 },
    { name: 'Hardik Pandya', score: 73 },
    { name: 'Ravindra Jadeja', score: 58 },
    { name: 'Rishabh Pant', score: 91 },
    { name: 'Jasprit Bumrah', score: 12 },
    { name: 'Mohammed Shami', score: 20 },
    { name: 'Kuldeep Yadav', score: 68 },
    { name: 'Shreyas Iyer', score: 55 }
  ];

  // ES6 map() to display all players
  const playerList = players.map((player, index) => (
    <li key={index}>
      {player.name} - Score: {player.score}
    </li>
  ));

  // ES6 arrow function: Filter players with scores below 70
  const lowScorers = players.filter(player => player.score < 70);

  const lowScorerList = lowScorers.map((player, index) => (
    <li key={index} style={{ color: 'red' }}>
      {player.name} - Score: {player.score}
    </li>
  ));

  return (
    <div style={{ padding: '20px' }}>
      <h2>List of Players (ES6 map)</h2>
      <ul>{playerList}</ul>

      <h2>Players with Score Below 70 (ES6 Arrow Function + filter)</h2>
      <ul>{lowScorerList}</ul>
    </div>
  );
}

export default ListofPlayers;
