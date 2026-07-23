import React, { Component } from 'react';
import Post from './Post';

class Posts extends Component {
  constructor(props) {
    super(props);
    // Initialize state with an empty list of Post
    this.state = {
      posts: []
    };
  }

  // Method to fetch posts using Fetch API
  loadPosts() {
    fetch('https://jsonplaceholder.typicode.com/posts')
      .then(response => response.json())
      .then(data => {
        const posts = data.map(item => new Post(item.id, item.title, item.body));
        this.setState({ posts });
      })
      .catch(error => {
        alert('Error loading posts: ' + error.message);
      });
  }

  // componentDidMount hook - called after component is mounted
  componentDidMount() {
    this.loadPosts();
  }

  // componentDidCatch hook - handles errors in child components
  componentDidCatch(error, info) {
    alert('Error in component: ' + error.message);
    console.error('componentDidCatch:', error, info);
  }

  // render method - displays title and body of each post
  render() {
    const { posts } = this.state;
    return (
      <div style={{ padding: '20px', maxWidth: '800px', margin: '0 auto' }}>
        <h1>Blog Posts</h1>
        {posts.length === 0 ? (
          <p>Loading posts...</p>
        ) : (
          posts.map(post => (
            <div key={post.id} style={{ marginBottom: '20px', borderBottom: '1px solid #ccc', paddingBottom: '10px' }}>
              <h3>{post.id}. {post.title}</h3>
              <p>{post.body}</p>
            </div>
          ))
        )}
      </div>
    );
  }
}

export default Posts;
