import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import Home from './pages/Home';
import CrearCliente from './pages/CrearCliente';
import CrearEvento from './pages/CrearEvento';
import CrearReserva from './pages/CrearReserva';
import ReservasPorEvento from './pages/ReservasPorEvento';
import ReservasPorCliente from './pages/ReservasPorCliente';

function App() {
  return (
    <>
    <Router>
      <Navbar />
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/clientes" element={<CrearCliente />} />
            <Route path="/eventos" element={<CrearEvento />} />
            <Route path="/reservas" element={<CrearReserva />} />
            <Route path="/eventos/:id/reservas" element={<ReservasPorEvento />} />
            <Route path="/clientes/:dni/reservas" element={<ReservasPorCliente />} />
          </Routes>
      </Router>
    </>
  );
}

export default App;