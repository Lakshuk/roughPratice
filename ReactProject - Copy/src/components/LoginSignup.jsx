import React, { useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../context/AuthContext";
import{ Link } from "react-router-dom";
import "./LoginSignup.css";

const LoginSignup = () => {
  const { register, login } = useContext(AuthContext);
  const [isLogin, setIsLogin] = useState(true);
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [email, setEmail] = useState("");
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    let result;
    if (isLogin) {
      result = await login(username, password);
      if (result.success) {
        console.log("Login Successful", result);
        navigate("/add-employee"); // Redirect after login
      }
      else{
        alert(result.message);    //show error message on invalid login
      }
    } else {
      result = await register(username, password, email);
      if (result.success) {
        console.log("Register Successful", result);
        setIsLogin(true); // Switch to login after successful registration
        navigate("/");
      }
    }
    setMessage(result.message);
  };

  return (
    <div>
      <h2>{isLogin ? 'Login' : 'Sign Up'}</h2>
      <form onSubmit={handleSubmit}>
        <label>Username:</label>
        <input
          type="text"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          required
        />
        {!isLogin && (
          <>
            <label>Email:</label>
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </>
        )}
        <label>Password:</label>
        <input
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
        <button type="submit">{isLogin ? 'Login' : 'Sign Up'}</button>
      </form>
      {message && <p>{message}</p>}
      <button onClick={() => setIsLogin(!isLogin)}>
        {isLogin ? "Don't have an account? Sign Up" : 'Already have an account? Login'}
      </button>

      <p>
        <Link to="/forgot-password">ForgotPassword</Link>
      </p>
    </div>
  );
};

export default LoginSignup;

