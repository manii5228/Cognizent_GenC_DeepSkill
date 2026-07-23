import React from 'react';
import CalculateScore from './Components/CalculateScore';

function App() {
  return (
    <div>
      <CalculateScore
        Name="Prathibha"
        School="VTU"
        Total={500}
        goal={425}
      />
    </div>
  );
}

export default App;
