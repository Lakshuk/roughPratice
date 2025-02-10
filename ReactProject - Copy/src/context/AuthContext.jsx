import React, { createContext, useContext, useState } from 'react';
import axios from 'axios';

export const AuthContext = createContext();
export const useAuth = () => useContext(AuthContext);

export const AuthProvider = ({ children }) => {
  const [currentUser, setCurrentUser] = useState(null);

  // Register function
  const register = async (username, password, email) => {
    try {
      const response = await axios.post("http://localhost:8080/employee/user/register", {
        username,
        password,
        email,
      });
      return { success: true, message: response.data };
    } catch (error) {
      return { success: false, message: error.response?.data || "An error occurred!" };
    }
  };

  // Login function
  const login = async (username, password) => {
    try {
      const response = await axios.post("http://localhost:8080/employee/user/login",
         { username, password });

         if(response.data === "Login successful!"){
          setCurrentUser(username);   //store user session
          return { success: true, message: "Login successful!" };
         }
         else{
          return { success: false, message: "Invalid credentials!" };
         }}
        catch(error){
          return { success: false, message: "Invalid credentials!" };
        }};



        //Forget password
        const forgotPassword = async(email) => {
          try{
            console.log("sending forget password for:", email);
            const response = await axios.post("http://localhost:8080/employee/forgot-password?email=${encodeURIComponent(email)}`)",
                { email });
            console.log("Response:", response.data);
            return { success : true, message : response.data};
          }
          catch(error){
            console.log("Error response:", error.response?.data);
            return { success : false, message : "User not found"};
          }
        };

        //Reset password
        const resetPassword = async(token, newPassword) => {
          try{
            const response = await axios.post("http://localhost:8080/employee/reset-password?token=${encodeURIComponent(token)}&newPassword=${encodeURIComponent(newPassword)}",
                { token, newPassword });
            return { success : true, message : response.data};
          }
          catch(error){
            return { success : false, message : error.response?.data ||"Invalid or expired token!"};
          }
        };


       // Store username in state
      //localStorage.setItem('currentUser', username); // Persist username in localStorage
    //   return { success: true, message: "Login successful!" };
    // } catch (error) {
    //   return { success: false, message: "Invalid credentials!" };
    //   console.log(response);
    

  

  // Logout function
  const logout = () => {
    setCurrentUser(null); // Remove from state
    // localStorage.removeItem('currentUser'); // Remove from localStorage
  };

  return (
    <AuthContext.Provider value={{ currentUser, register, login, logout, forgotPassword, resetPassword }}>
      {children}
    </AuthContext.Provider>
  );
};