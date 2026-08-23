
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Home from "./pages/Home";
import Income from "./pages/Income";
import Expence from "./pages/Expence";
import Category from "./pages/Category";
import Filter from "./pages/Filter";
import Login from "./pages/Login";
import SignUp from "./pages/SignUp";

import {Toaster} from "react-hot-toast";
const App = () => {
  
  return(
    <div>
          <Toaster></Toaster>
          <BrowserRouter>
             <Routes>
                <Route path="/dashboard" element={<Home/>}></Route>
                <Route path="/income" element={<Income/>}></Route>
                <Route path="/expence" element={<Expence/>}></Route>
                <Route path="/category" element={<Category/>}></Route>
                <Route path="/filter" element={<Filter/>}></Route>
                <Route path="/login" element={<Login/>}></Route>
                  <Route path="/signup" element={<SignUp/>} />                                              
             </Routes>
          </BrowserRouter>
    </div>
  )
    
  
}
export default App;
