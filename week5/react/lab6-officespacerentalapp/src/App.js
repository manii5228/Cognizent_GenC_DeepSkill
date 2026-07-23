import React from 'react';

// Office object for single display
const office = {
  name: 'Prestige Tech Park',
  rent: 75000,
  address: 'Marathahalli, Bengaluru, Karnataka 560037'
};

// List of office objects
const officeList = [
  { id: 1, name: 'Prestige Tech Park', rent: 75000, address: 'Marathahalli, Bengaluru' },
  { id: 2, name: 'Embassy Golf Links', rent: 55000, address: 'Indiranagar, Bengaluru' },
  { id: 3, name: 'Manyata Tech Park', rent: 90000, address: 'Nagavara, Bengaluru' },
  { id: 4, name: 'Global Village Tech Park', rent: 48000, address: 'Mylasandra, Bengaluru' },
  { id: 5, name: 'Cessna Business Park', rent: 62000, address: 'Kadubeesanahalli, Bengaluru' }
];

function App() {
  // JSX element for heading
  const heading = <h1>Office Space Rental App</h1>;

  // JSX attribute for image (using a placeholder image URL)
  const officeImage = (
    <img
      src="https://images.unsplash.com/photo-1497366216548-37526070297c?w=600"
      alt="Office Space"
      style={{ width: '100%', maxWidth: '600px', borderRadius: '10px', marginBottom: '20px' }}
    />
  );

  return (
    <div style={{ fontFamily: 'Arial, sans-serif', padding: '20px', maxWidth: '800px', margin: '0 auto' }}>
      {/* JSX Heading element */}
      {heading}

      {/* JSX Image attribute */}
      {officeImage}

      {/* Single office object display */}
      <h2>Featured Office Space</h2>
      <div style={{ border: '1px solid #ccc', padding: '15px', borderRadius: '8px', marginBottom: '30px' }}>
        <p><strong>Name:</strong> {office.name}</p>
        <p>
          <strong>Rent:</strong>{' '}
          {/* Inline CSS: Red if rent < 60000, Green if > 60000 */}
          <span style={{ color: office.rent < 60000 ? 'red' : 'green', fontWeight: 'bold' }}>
            ₹{office.rent.toLocaleString()} / month
          </span>
        </p>
        <p><strong>Address:</strong> {office.address}</p>
      </div>

      {/* List of office spaces using JSX map */}
      <h2>All Available Office Spaces</h2>
      <ul style={{ listStyle: 'none', padding: 0 }}>
        {officeList.map(item => (
          <li key={item.id} style={{
            border: '1px solid #ddd',
            padding: '15px',
            marginBottom: '12px',
            borderRadius: '8px',
            backgroundColor: '#f9f9f9'
          }}>
            <h3 style={{ margin: '0 0 8px 0' }}>{item.name}</h3>
            <p style={{ margin: '4px 0' }}>
              <strong>Rent:</strong>{' '}
              {/* Inline CSS: conditional color based on rent */}
              <span style={{ color: item.rent < 60000 ? 'red' : 'green', fontWeight: 'bold' }}>
                ₹{item.rent.toLocaleString()} / month
              </span>
              {item.rent < 60000 ? ' (Below Budget)' : ' (Premium)'}
            </p>
            <p style={{ margin: '4px 0' }}><strong>Address:</strong> {item.address}</p>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default App;
