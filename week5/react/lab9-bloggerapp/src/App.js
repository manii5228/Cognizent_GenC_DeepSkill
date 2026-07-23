import React, { Component } from 'react';

// ===== Component 1: Book Details =====
function BookDetails({ books }) {
  // Conditional rendering with && operator
  return (
    <div style={cardStyle('#e3f2fd')}>
      <h2 style={{ color: '#1565c0' }}>📚 Book Details</h2>
      {books && books.length > 0 ? (
        <ul>
          {books.map((book, i) => (
            <li key={i} style={{ marginBottom: '8px' }}>
              <strong>{book.title}</strong> by {book.author} — <em>{book.genre}</em>
            </li>
          ))}
        </ul>
      ) : (
        <p>No books available.</p>
      )}
    </div>
  );
}

// ===== Component 2: Blog Details =====
// Uses if-else conditional rendering
function BlogDetails({ blogs, showBlogs }) {
  // Method 1: if-else
  let content;
  if (showBlogs) {
    content = (
      <ul>
        {blogs.map((blog, i) => (
          <li key={i} style={{ marginBottom: '8px' }}>
            <strong>{blog.title}</strong> — {blog.date} — <span style={{ color: '#666' }}>{blog.summary}</span>
          </li>
        ))}
      </ul>
    );
  } else {
    content = <p style={{ color: '#888' }}>Blogs are currently hidden.</p>;
  }

  return (
    <div style={cardStyle('#f3e5f5')}>
      <h2 style={{ color: '#6a1b9a' }}>📝 Blog Details</h2>
      {content}
    </div>
  );
}

// ===== Component 3: Course Details =====
// Uses switch-case (via a helper) and ternary
function CourseDetails({ category }) {
  // Method: switch-case for different course categories
  function getCourses(cat) {
    switch (cat) {
      case 'web':
        return ['HTML & CSS', 'JavaScript', 'React', 'Node.js'];
      case 'data':
        return ['Python', 'Machine Learning', 'Data Analysis', 'Deep Learning'];
      case 'mobile':
        return ['Android Development', 'iOS Development', 'Flutter', 'React Native'];
      default:
        return ['No courses found'];
    }
  }

  const courses = getCourses(category);

  return (
    <div style={cardStyle('#e8f5e9')}>
      <h2 style={{ color: '#2e7d32' }}>🎓 Course Details — Category: <em>{category}</em></h2>
      {/* Ternary operator */}
      {courses.length > 0 ? (
        <ol>
          {courses.map((course, i) => (
            <li key={i}>{course}</li>
          ))}
        </ol>
      ) : (
        <p>No courses available.</p>
      )}
    </div>
  );
}

// ===== App Component (controls rendering with state) =====
class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      showBlogs: true,
      courseCategory: 'web',
      activeSection: 'all'  // controls which section to show
    };
  }

  render() {
    const books = [
      { title: 'Clean Code', author: 'Robert C. Martin', genre: 'Programming' },
      { title: 'The Pragmatic Programmer', author: 'Andrew Hunt', genre: 'Software Engineering' },
      { title: 'Design Patterns', author: 'Gang of Four', genre: 'Architecture' }
    ];

    const blogs = [
      { title: 'Getting Started with React', date: '2024-01-15', summary: 'A beginner guide to React.' },
      { title: 'Understanding Hooks', date: '2024-02-10', summary: 'useState and useEffect explained.' },
      { title: 'State Management', date: '2024-03-05', summary: 'Redux vs Context API.' }
    ];

    const { showBlogs, courseCategory, activeSection } = this.state;

    return (
      <div style={{ fontFamily: 'Arial, sans-serif', maxWidth: '900px', margin: '30px auto', padding: '20px' }}>
        <h1>📰 Blogger App</h1>
        <p style={{ color: '#555' }}>Demonstrating multiple ways of Conditional Rendering in React</p>

        {/* Navigation buttons */}
        <div style={{ marginBottom: '25px' }}>
          <button id="btn-show-all" onClick={() => this.setState({ activeSection: 'all' })} style={navBtn('#607d8b', activeSection === 'all')}>All</button>
          <button id="btn-show-books" onClick={() => this.setState({ activeSection: 'books' })} style={navBtn('#1565c0', activeSection === 'books')}>Books Only</button>
          <button id="btn-show-blogs" onClick={() => this.setState({ activeSection: 'blogs' })} style={navBtn('#6a1b9a', activeSection === 'blogs')}>Blogs Only</button>
          <button id="btn-show-courses" onClick={() => this.setState({ activeSection: 'courses' })} style={navBtn('#2e7d32', activeSection === 'courses')}>Courses Only</button>
        </div>

        {/* Toggle blog visibility */}
        <div style={{ marginBottom: '20px' }}>
          <button id="btn-toggle-blogs" onClick={() => this.setState(prev => ({ showBlogs: !prev.showBlogs }))} style={navBtn('#e65100', false)}>
            {showBlogs ? 'Hide Blogs' : 'Show Blogs'}
          </button>

          <select
            id="select-category"
            value={courseCategory}
            onChange={(e) => this.setState({ courseCategory: e.target.value })}
            style={{ marginLeft: '15px', padding: '8px', borderRadius: '5px', border: '1px solid #ccc' }}
          >
            <option value="web">Web Development</option>
            <option value="data">Data Science</option>
            <option value="mobile">Mobile Development</option>
          </select>
        </div>

        {/* 
          Conditional Rendering Methods:
          1. Ternary operator (activeSection === 'all')
          2. Logical && operator (inside BookDetails)
          3. if-else (inside BlogDetails)
          4. switch-case (inside CourseDetails)
          5. Element variables (activeSection nav above)
        */}

        {/* Method 5: Element variables – show based on activeSection state */}
        {(activeSection === 'all' || activeSection === 'books') && (
          <BookDetails books={books} />
        )}

        {(activeSection === 'all' || activeSection === 'blogs') && (
          <BlogDetails blogs={blogs} showBlogs={showBlogs} />
        )}

        {(activeSection === 'all' || activeSection === 'courses') && (
          <CourseDetails category={courseCategory} />
        )}
      </div>
    );
  }
}

// ===== Styles =====
const cardStyle = (bg) => ({
  backgroundColor: bg,
  borderRadius: '10px',
  padding: '20px',
  marginBottom: '20px',
  border: '1px solid #ddd'
});

const navBtn = (color, active) => ({
  backgroundColor: active ? color : '#ccc',
  color: active ? 'white' : '#333',
  border: 'none',
  padding: '10px 18px',
  borderRadius: '6px',
  cursor: 'pointer',
  marginRight: '10px',
  fontWeight: active ? 'bold' : 'normal'
});

export default App;
