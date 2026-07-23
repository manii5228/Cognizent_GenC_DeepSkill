import React from 'react';
import '../Stylesheets/mystyle.css';

function CalculateScore(props) {
  const { Name, School, Total, goal } = props;

  // Calculate average score (goal is out of Total marks)
  const average = ((goal / Total) * 100).toFixed(2);

  return (
    <div className="score-card">
      <h2>Student Score Card</h2>
      <table className="score-table">
        <tbody>
          <tr>
            <th>Student Name</th>
            <td>{Name}</td>
          </tr>
          <tr>
            <th>School</th>
            <td>{School}</td>
          </tr>
          <tr>
            <th>Total Marks</th>
            <td>{Total}</td>
          </tr>
          <tr>
            <th>Scored Marks (Goal)</th>
            <td>{goal}</td>
          </tr>
          <tr>
            <th>Average Score</th>
            <td className="average">{average}%</td>
          </tr>
        </tbody>
      </table>
    </div>
  );
}

export default CalculateScore;
