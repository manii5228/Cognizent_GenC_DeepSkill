import React from 'react';
import ListofPlayers from './components/ListofPlayers';
import IndianPlayers from './components/IndianPlayers';

// flag = true → shows ListofPlayers, flag = false → shows IndianPlayers
const flag = true;

function App() {
  let component;
  if (flag) {
    component = <ListofPlayers />;
  } else {
    component = <IndianPlayers />;
  }

  return (
    <div>
      <h1>Cricket App - ES6 Features</h1>
      <p style={{ color: 'blue' }}>Flag is currently: <strong>{flag.toString()}</strong></p>
      {component}
    </div>
  );
}

export default App;
