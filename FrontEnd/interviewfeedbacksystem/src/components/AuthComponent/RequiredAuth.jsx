import React from 'react'
import { Navigate, useLocation } from 'react-router-dom';
import { useAuth } from './AuthProvider';

function RequiredAuth({children}) {
    const location=useLocation();
    const auth= useAuth();
    if(auth.isLoggedIn=="")
    {
        return <Navigate to="/user-login" state={{path:location.pathname}}/>
    }
    return children;
}

export default RequiredAuth