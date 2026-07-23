import React, { Component } from 'react';

// ---- Flight data ----
const flights = [
  { id: 1, from: 'Bengaluru (BLR)', to: 'Mumbai (BOM)', time: '08:00 AM', price: '₹3,500', airline: 'IndiGo' },
  { id: 2, from: 'Bengaluru (BLR)', to: 'Delhi (DEL)',  time: '10:30 AM', price: '₹5,200', airline: 'Air India' },
  { id: 3, from: 'Bengaluru (BLR)', to: 'Chennai (MAA)',time: '02:00 PM', price: '₹2,100', airline: 'SpiceJet' },
  { id: 4, from: 'Bengaluru (BLR)', to: 'Kolkata (CCU)',time: '06:00 PM', price: '₹6,800', airline: 'Vistara' }
];

// ---- GuestPage Component ----
function GuestPage() {
  return (
    <div style={pageStyle('#fff8e1')}>
      <h2 style={{ color: '#f57f17' }}>🌍 Welcome, Guest! Browse Available Flights</h2>
      <p style={{ color: '#666' }}>Please <strong>Login</strong> to book a ticket.</p>
      <table style={tableStyle}>
        <thead>
          <tr style={{ backgroundColor: '#ffd54f' }}>
            <th style={thStyle}>Flight #</th>
            <th style={thStyle}>From</th>
            <th style={thStyle}>To</th>
            <th style={thStyle}>Time</th>
            <th style={thStyle}>Price</th>
            <th style={thStyle}>Airline</th>
          </tr>
        </thead>
        <tbody>
          {flights.map(f => (
            <tr key={f.id} style={{ borderBottom: '1px solid #eee' }}>
              <td style={tdStyle}>{f.id}</td>
              <td style={tdStyle}>{f.from}</td>
              <td style={tdStyle}>{f.to}</td>
              <td style={tdStyle}>{f.time}</td>
              <td style={tdStyle}>{f.price}</td>
              <td style={tdStyle}>{f.airline}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

// ---- UserPage Component (logged-in user can book) ----
class UserPage extends Component {
  constructor(props) {
    super(props);
    this.state = { selectedFlight: null, booked: false };
    this.handleBook = this.handleBook.bind(this);
  }

  handleBook(flight) {
    this.setState({ selectedFlight: flight, booked: true });
  }

  render() {
    const { booked, selectedFlight } = this.state;
    return (
      <div style={pageStyle('#e8f5e9')}>
        <h2 style={{ color: '#2e7d32' }}>✅ Welcome, User! Book Your Flight</h2>
        {booked ? (
          <div style={{ backgroundColor: '#c8e6c9', padding: '15px', borderRadius: '8px' }}>
            <h3>🎉 Booking Confirmed!</h3>
            <p><strong>Flight:</strong> {selectedFlight.from} → {selectedFlight.to}</p>
            <p><strong>Time:</strong> {selectedFlight.time}</p>
            <p><strong>Price:</strong> {selectedFlight.price}</p>
            <p><strong>Airline:</strong> {selectedFlight.airline}</p>
          </div>
        ) : (
          <table style={tableStyle}>
            <thead>
              <tr style={{ backgroundColor: '#a5d6a7' }}>
                <th style={thStyle}>From</th>
                <th style={thStyle}>To</th>
                <th style={thStyle}>Time</th>
                <th style={thStyle}>Price</th>
                <th style={thStyle}>Airline</th>
                <th style={thStyle}>Action</th>
              </tr>
            </thead>
            <tbody>
              {flights.map(f => (
                <tr key={f.id} style={{ borderBottom: '1px solid #eee' }}>
                  <td style={tdStyle}>{f.from}</td>
                  <td style={tdStyle}>{f.to}</td>
                  <td style={tdStyle}>{f.time}</td>
                  <td style={tdStyle}>{f.price}</td>
                  <td style={tdStyle}>{f.airline}</td>
                  <td style={tdStyle}>
                    <button
                      id={`book-flight-${f.id}`}
                      onClick={() => this.handleBook(f)}
                      style={btnStyle}
                    >
                      Book
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    );
  }
}

// ---- App (Conditional Rendering: Login / Logout) ----
class App extends Component {
  constructor(props) {
    super(props);
    this.state = { isLoggedIn: false };
    this.handleLogin = this.handleLogin.bind(this);
    this.handleLogout = this.handleLogout.bind(this);
  }

  handleLogin() {
    this.setState({ isLoggedIn: true });
  }

  handleLogout() {
    this.setState({ isLoggedIn: false });
  }

  render() {
    const { isLoggedIn } = this.state;

    return (
      <div style={{ fontFamily: 'Arial, sans-serif', maxWidth: '900px', margin: '30px auto', padding: '20px' }}>
        <h1>✈️ Ticket Booking App</h1>

        <div style={{ marginBottom: '20px' }}>
          {/* Conditional rendering of Login/Logout button */}
          {isLoggedIn ? (
            <button id="btn-logout" onClick={this.handleLogout} style={{ ...btnStyle, backgroundColor: '#f44336' }}>
              Logout
            </button>
          ) : (
            <button id="btn-login" onClick={this.handleLogin} style={{ ...btnStyle, backgroundColor: '#4CAF50' }}>
              Login
            </button>
          )}
          <span style={{ marginLeft: '15px', color: '#555' }}>
            Status: <strong>{isLoggedIn ? 'Logged In' : 'Guest'}</strong>
          </span>
        </div>

        {/* Conditional Rendering: show UserPage or GuestPage */}
        {isLoggedIn ? <UserPage /> : <GuestPage />}
      </div>
    );
  }
}

// ---- Styles ----
const pageStyle = (bg) => ({
  backgroundColor: bg,
  border: '1px solid #ddd',
  borderRadius: '10px',
  padding: '20px'
});

const tableStyle = {
  width: '100%',
  borderCollapse: 'collapse',
  marginTop: '15px'
};

const thStyle = {
  padding: '10px',
  textAlign: 'left',
  fontWeight: 'bold'
};

const tdStyle = {
  padding: '10px',
  color: '#333'
};

const btnStyle = {
  backgroundColor: '#2196F3',
  color: 'white',
  border: 'none',
  padding: '8px 16px',
  borderRadius: '5px',
  cursor: 'pointer'
};

export default App;
