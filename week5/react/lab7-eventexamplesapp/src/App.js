import React, { Component } from 'react';

// ---- Counter Component (Increment/Decrement with multiple methods) ----
class Counter extends Component {
  constructor(props) {
    super(props);
    this.state = { count: 0, message: '' };
    this.increment = this.increment.bind(this);
    this.decrement = this.decrement.bind(this);
    this.sayHello = this.sayHello.bind(this);
    this.handleIncrement = this.handleIncrement.bind(this);
  }

  // Method 1: increment value
  increment() {
    this.setState(prev => ({ count: prev.count + 1 }));
  }

  // Method 2: say hello with static message
  sayHello() {
    this.setState({ message: 'Hello! Welcome to React Event Handling.' });
  }

  // handleIncrement invokes multiple methods on Increment click
  handleIncrement() {
    this.increment();
    this.sayHello();
  }

  decrement() {
    this.setState(prev => ({ count: prev.count - 1 }));
  }

  render() {
    return (
      <div style={sectionStyle}>
        <h2>Counter - Increment / Decrement</h2>
        <h3>Count: {this.state.count}</h3>
        {/* Increment button invokes multiple methods */}
        <button id="btn-increment" onClick={this.handleIncrement} style={btnStyle('#4CAF50')}>Increment</button>
        <button id="btn-decrement" onClick={this.decrement} style={btnStyle('#f44336')}>Decrement</button>
        {this.state.message && <p style={{ color: '#333', marginTop: '10px' }}>{this.state.message}</p>}
      </div>
    );
  }
}

// ---- SayWelcome Component (passing argument to event handler) ----
class SayWelcome extends Component {
  handleClick(greet) {
    alert(greet + '! Thank you for visiting.');
  }

  render() {
    return (
      <div style={sectionStyle}>
        <h2>Say Welcome - Passing Argument to Handler</h2>
        {/* Passes "welcome" as argument using arrow function */}
        <button id="btn-say-welcome" onClick={() => this.handleClick('welcome')} style={btnStyle('#2196F3')}>
          Say Welcome
        </button>
      </div>
    );
  }
}

// ---- SyntheticEvent Component (OnPress synthetic event) ----
class SyntheticEventDemo extends Component {
  // Synthetic event handler
  onPress(e) {
    e.preventDefault();
    alert('I was clicked');
    console.log('Synthetic Event:', e);
  }

  render() {
    return (
      <div style={sectionStyle}>
        <h2>Synthetic Event - OnPress</h2>
        <button id="btn-synthetic" onClick={(e) => this.onPress(e)} style={btnStyle('#9C27B0')}>
          Click Me (Synthetic Event)
        </button>
      </div>
    );
  }
}

// ---- CurrencyConvertor Component (INR to Euro) ----
class CurrencyConvertor extends Component {
  constructor(props) {
    super(props);
    this.state = {
      inr: '',
      euro: null
    };
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleChange = this.handleChange.bind(this);
  }

  handleChange(e) {
    this.setState({ inr: e.target.value });
  }

  // Handle the conversion: 1 Euro = approx 90 INR
  handleSubmit(e) {
    e.preventDefault();
    const inrValue = parseFloat(this.state.inr);
    if (!isNaN(inrValue)) {
      const euroValue = (inrValue / 90).toFixed(4);
      this.setState({ euro: euroValue });
    } else {
      alert('Please enter a valid number');
    }
  }

  render() {
    return (
      <div style={sectionStyle}>
        <h2>Currency Convertor (INR → Euro)</h2>
        <form onSubmit={this.handleSubmit}>
          <label htmlFor="inr-input"><strong>Enter Amount in INR (₹): </strong></label>
          <input
            id="inr-input"
            type="number"
            value={this.state.inr}
            onChange={this.handleChange}
            placeholder="e.g. 10000"
            style={{ padding: '8px', marginLeft: '10px', marginRight: '10px', borderRadius: '4px', border: '1px solid #ccc' }}
          />
          <button id="btn-convert" type="submit" style={btnStyle('#FF9800')}>Convert</button>
        </form>
        {this.state.euro !== null && (
          <p style={{ marginTop: '15px', fontSize: '1.1rem' }}>
            ₹{this.state.inr} = <strong>€{this.state.euro}</strong>
            <small style={{ color: '#888' }}> (Rate: 1 Euro = ₹90)</small>
          </p>
        )}
      </div>
    );
  }
}

// ---- Styles ----
const sectionStyle = {
  border: '1px solid #ddd',
  borderRadius: '10px',
  padding: '20px',
  marginBottom: '20px',
  backgroundColor: '#fafafa'
};

const btnStyle = (color) => ({
  backgroundColor: color,
  color: 'white',
  border: 'none',
  padding: '10px 20px',
  borderRadius: '6px',
  cursor: 'pointer',
  marginRight: '10px',
  fontSize: '14px'
});

// ---- App ----
function App() {
  return (
    <div style={{ fontFamily: 'Arial, sans-serif', maxWidth: '700px', margin: '30px auto', padding: '20px' }}>
      <h1>Event Examples App</h1>
      <Counter />
      <SayWelcome />
      <SyntheticEventDemo />
      <CurrencyConvertor />
    </div>
  );
}

export default App;
