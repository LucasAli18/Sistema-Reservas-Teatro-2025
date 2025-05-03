import { Link } from 'react-router-dom';

export default function Navbar() {
  return (
    <nav style={{ marginBottom: '20px' }}>
      <Link to="/">Eventos</Link> |{' '}
      <Link to="/clientes">Clientes</Link> |{' '}
      <Link to="/eventos">Eventos</Link> |{' '}
      <Link to="/reservas">Reservas</Link>
    </nav>
  );
}