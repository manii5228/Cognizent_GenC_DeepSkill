import React from 'react';

function IndianPlayers() {
  // ES6 Destructuring: Odd and Even team players
  const allPlayers = [
    'Virat Kohli',
    'Rohit Sharma',
    'Shubman Gill',
    'KL Rahul',
    'Hardik Pandya',
    'Ravindra Jadeja',
    'Rishabh Pant',
    'Jasprit Bumrah',
    'Mohammed Shami',
    'Kuldeep Yadav',
    'Shreyas Iyer'
  ];

  // ES6 Destructuring to separate odd and even indexed players
  const oddPlayers = allPlayers.filter((_, index) => index % 2 !== 0);
  const evenPlayers = allPlayers.filter((_, index) => index % 2 === 0);

  // ES6 Spread Operator: Merge two arrays
  const T20players = ['Virat Kohli', 'Rohit Sharma', 'KL Rahul', 'Hardik Pandya', 'Jasprit Bumrah'];
  const RanjiTrophyPlayers = ['Mayank Agarwal', 'Prithvi Shaw', 'Devdutt Padikkal', 'Arjun Tendulkar', 'Ishan Kishan'];

  // ES6 Merge (Spread operator)
  const mergedPlayers = [...T20players, ...RanjiTrophyPlayers];

  return (
    <div style={{ padding: '20px' }}>
      <h2>Indian Players</h2>

      <h3>Odd Team Players (ES6 Destructuring)</h3>
      <ul>
        {oddPlayers.map((player, i) => (
          <li key={i}>{player}</li>
        ))}
      </ul>

      <h3>Even Team Players (ES6 Destructuring)</h3>
      <ul>
        {evenPlayers.map((player, i) => (
          <li key={i}>{player}</li>
        ))}
      </ul>

      <h3>T20 Players</h3>
      <ul>
        {T20players.map((player, i) => (
          <li key={i}>{player}</li>
        ))}
      </ul>

      <h3>Ranji Trophy Players</h3>
      <ul>
        {RanjiTrophyPlayers.map((player, i) => (
          <li key={i}>{player}</li>
        ))}
      </ul>

      <h3>Merged Players (ES6 Spread Operator)</h3>
      <ul>
        {mergedPlayers.map((player, i) => (
          <li key={i}>{player}</li>
        ))}
      </ul>
    </div>
  );
}

export default IndianPlayers;
