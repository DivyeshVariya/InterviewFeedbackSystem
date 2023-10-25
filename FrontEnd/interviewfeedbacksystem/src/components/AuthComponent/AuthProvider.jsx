import React, { createContext, useContext, useState } from 'react'

const AuthContext = createContext(null);

function AuthProvider({ children }) {
    const [isLoggedIn, setIsLoggedIn] = useState("");

    const login = (token) => {
        setIsLoggedIn(token);
        window.localStorage.setItem("loggedIn", token);
    }

    const logout = () => {
        setIsLoggedIn("");
        window.localStorage.clear();
    }
    return (
        <AuthContext.Provider value={{ isLoggedIn, login, logout }}>
            {children}
        </AuthContext.Provider>
    )
}

export default AuthProvider

export const useAuth = () => {
    return useContext(AuthContext);
};