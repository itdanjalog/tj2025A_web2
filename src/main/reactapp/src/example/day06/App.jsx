import { BrowserRouter, Route, Routes } from "react-router-dom";
import Header from "./components/Header";
import CartPage from "./pages/CartPage";
import MenuPage from "./pages/MenuPage";
import HomePage from "./pages/HomePage";

export default function App( props ){
    return<>
        <BrowserRouter>
            <h3> 루트페이지 </h3>
            <Header />
            <Routes>
                <Route path="/" element={ <HomePage /> } />
                <Route path="/menu" element={ <MenuPage /> } />
                <Route path="/cart" element={ <CartPage /> } />
            </Routes>
        </BrowserRouter>
    </>
}