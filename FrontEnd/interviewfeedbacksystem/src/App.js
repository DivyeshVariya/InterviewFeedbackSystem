import { BrowserRouter, Route, Router, Routes } from "react-router-dom";
import "./App.css";
import Home from "./components/Home";
import Login from "./components/Login";
import Register from "./components/Register";
import Aboutus from "./components/Aboutus";
import Contactus from "./components/Contactus";
import Services from "./components/Services";
import Error from "./components/Error";
import ForgetPassword from "./components/ForgetPassword";
import AuthProvider from "./components/AuthComponent/AuthProvider";
import RequiredAuth from "./components/AuthComponent/RequiredAuth";
import Dashboard from "./components/Dashboard";
import FeedbackForm from "./components/FeedbackForm";
import UploadFeedback from "./components/UploadFeedback";
import AllFeedbacks from "./components/AllFeedbacks";
import Report from "./components/Report";
import ViewFeedback from "./components/ViewFeedback";
import UpdateFeedback from "./components/UpdateFeedback";

function App() {
  return (
      <BrowserRouter>
      <AuthProvider>
      <Routes>
        <Route>
          <Route path="/" element={<Home />}/>
          <Route path="/user-login" element={<Login/>}/>
          <Route path="/register-user" element={<Register />}/>
          <Route path="/about-us" element={<Aboutus />}/>
          <Route path="/contact-us" element={<Contactus />}/>
          <Route path="/services" element={<Services />}/>
          <Route path="/dash-board" element={<RequiredAuth><Dashboard /></RequiredAuth>}/>
          <Route path="/new-feedback" element={<RequiredAuth><FeedbackForm /></RequiredAuth>}/>
          <Route path="/upload-feedbacks" element={<RequiredAuth><UploadFeedback /></RequiredAuth>}/>
          <Route path="/view-all-feedbacks" element={<RequiredAuth><AllFeedbacks/></RequiredAuth>}/>
          <Route path="/view-feedback-details" element={<RequiredAuth><ViewFeedback/></RequiredAuth>}/>
          <Route path="/update-feedback-details" element={<RequiredAuth><UpdateFeedback /></RequiredAuth>}/>
          <Route path="/generate-report" element={<RequiredAuth><Report /></RequiredAuth>}/>
          <Route path="/forget-password" element={<ForgetPassword />}/>
          <Route path="*" element={<Error />}/>
      </Route>
      </Routes>
      </AuthProvider>
      </BrowserRouter>
  );
}

export default App;
